package com.clara.ops.challenge.documents.domain.services;

import com.clara.ops.challenge.documents.domain.exceptions.ErrorWhenTriedToDeleteTmpFile;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

// Domain
import com.clara.ops.challenge.documents.domain.exceptions.ErrorWhenTriedToCreateTmpFile;


public class TemporaryFileManagerService {
  public static void createTemporaryFile(Path tempFilePath, InputStream inputStreamUserFile)
      throws ErrorWhenTriedToCreateTmpFile {

    // Create a temporary file
    try (OutputStream outputStream = Files.newOutputStream(tempFilePath); inputStreamUserFile) {

      int bytesRead = 0;
      byte[] buffer = new byte[8192]; // 8KB buffer
      while ((bytesRead = inputStreamUserFile.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
    } catch (Exception e) {
      throw new ErrorWhenTriedToCreateTmpFile("Error creating temporary file".formatted(tempFilePath.getFileName()));
    }
  }

  public static void delete(Path tempFilePath) throws ErrorWhenTriedToDeleteTmpFile {
    try {
      Files.delete(tempFilePath);
    } catch (Exception e) {
      throw new ErrorWhenTriedToDeleteTmpFile("Error deleting temporary file".formatted(tempFilePath.getFileName()));
    }
  }
}
