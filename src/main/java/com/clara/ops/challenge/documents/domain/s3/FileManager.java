package com.clara.ops.challenge.documents.domain.s3;

import java.nio.file.Path;

public interface FileManager {
  void upload(Path filePath, String bucketName, String keyName);

  void createIfNotExists(String bucketName);
}
