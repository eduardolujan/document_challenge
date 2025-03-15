package com.clara.ops.challenge.documents.infrastructure.s3;

import com.amazonaws.services.s3.model.CreateBucketRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;

// Domain
import com.clara.ops.challenge.documents.domain.s3.FileManager;


@Service
public class AwsFileManager implements FileManager {

  private final AmazonS3 amazonS3;
  private static final int PART_SIZE = 50 * 1024 * 1024; // 50MB

  public AwsFileManager(AmazonS3 amazonS3) {
    this.amazonS3 = amazonS3;
  }


  @Override
  public void upload(Path filePath, String bucketName, String keyName) {
    // Step 1: Initiate multipart upload
    InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, keyName);
    InitiateMultipartUploadResult initResponse = amazonS3.initiateMultipartUpload(initRequest);
    String uploadId = initResponse.getUploadId();

    List<PartETag> partETags = new ArrayList<>();
    File file = filePath.toFile();

    int partNumber = 1;

    try (InputStream inputStream = new FileInputStream(file)) {
      byte[] buffer = new byte[PART_SIZE];
      int bytesRead;

      while ((bytesRead = inputStream.read(buffer)) > 0) {
        UploadPartRequest uploadRequest = new UploadPartRequest()
            .withBucketName(bucketName)
            .withKey(keyName)
            .withUploadId(uploadId)
            .withPartNumber(partNumber)
            .withInputStream(new java.io.ByteArrayInputStream(buffer, 0, bytesRead))
            .withPartSize(bytesRead);

        UploadPartResult uploadResult = amazonS3.uploadPart(uploadRequest);
        partETags.add(uploadResult.getPartETag());
        partNumber++;

      }

      // Step 3: Complete multipart upload
      CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest(
          bucketName, keyName, uploadId, partETags);
      amazonS3.completeMultipartUpload(completeRequest);

    } catch (Exception e) {
      // Abort in case of failure
      amazonS3.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, keyName, uploadId));
      throw new RuntimeException("Multipart upload failed: " + e.getMessage(), e);
    }
  }

  @Override
  public void createIfNotExists(String bucketName) {

    if (!amazonS3.doesBucketExistV2(bucketName)) {
      amazonS3.createBucket(new CreateBucketRequest(bucketName));
    }

  }


}
