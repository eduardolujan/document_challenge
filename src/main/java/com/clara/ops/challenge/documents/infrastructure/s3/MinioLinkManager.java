package com.clara.ops.challenge.documents.infrastructure.s3;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.springframework.stereotype.Service;

// Domain
import com.clara.ops.challenge.documents.domain.s3.LinkManager;


@Service
public class MinioLinkManager implements LinkManager {
    private final MinioClient minioClient;

    public MinioLinkManager(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public String generateLink(String bucketName, String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(60 * 60 * 24)
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error generating link", e);
        }
    }

}
