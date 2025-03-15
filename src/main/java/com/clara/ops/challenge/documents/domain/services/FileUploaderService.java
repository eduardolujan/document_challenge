package com.clara.ops.challenge.documents.domain.services;

import com.clara.ops.challenge.documents.domain.s3.FileManager;
import java.nio.file.Path;

public class FileUploaderService {

  public static void upload(
      FileManager fileManager,
      Path fileTempPath,
      String bucketName,
      String document,
      String userName) {
    fileManager.createIfNotExists(bucketName);
    String path = String.format("%s/%s", userName, document);
    fileManager.upload(fileTempPath, bucketName, path);
  }
}
