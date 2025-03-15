package com.clara.ops.challenge.documents.domain.s3;

public interface LinkManager {
  String generateLink(String bucketName, String objectName);
}
