package com.clara.ops.challenge.documents.domain.services;

import static org.junit.jupiter.api.Assertions.*;

import com.clara.ops.challenge.documents.domain.exceptions.ErrorWhenTriedToCreateTmpFile;
import com.clara.ops.challenge.documents.domain.exceptions.ErrorWhenTriedToDeleteTmpFile;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TemporaryFileManagerServiceTest {

  @TempDir Path tempDir;

  @Test
  void createTemporaryFile_createsFileSuccessfully() throws Exception {
    Path tempFilePath = tempDir.resolve("tempFile.txt");
    InputStream inputStream = new ByteArrayInputStream("Test content".getBytes());

    TemporaryFileManagerService.createTemporaryFile(tempFilePath, inputStream);

    assertTrue(Files.exists(tempFilePath));
    assertEquals("Test content", Files.readString(tempFilePath));
  }

  @Test
  void createTemporaryFile_throwsErrorWhenInputStreamIsNull() {
    Path tempFilePath = tempDir.resolve("tempFile.txt");

    assertThrows(
        ErrorWhenTriedToCreateTmpFile.class,
        () -> {
          TemporaryFileManagerService.createTemporaryFile(tempFilePath, null);
        });
  }

  @Test
  void createTemporaryFile_throwsErrorWhenPathIsInvalid() {
    Path tempFilePath = tempDir.resolve("\0invalidFile.txt");
    InputStream inputStream = new ByteArrayInputStream("Test content".getBytes());

    assertThrows(
        ErrorWhenTriedToCreateTmpFile.class,
        () -> {
          TemporaryFileManagerService.createTemporaryFile(tempFilePath, inputStream);
        });
  }

  @Test
  void delete_deletesFileSuccessfully() throws Exception {
    Path tempFilePath = tempDir.resolve("tempFile.txt");
    Files.createFile(tempFilePath);

    TemporaryFileManagerService manager = new TemporaryFileManagerService();
    manager.delete(tempFilePath);

    assertFalse(Files.exists(tempFilePath));
  }

  @Test
  void delete_throwsErrorWhenFileDoesNotExist() {
    Path tempFilePath = tempDir.resolve("nonExistentFile.txt");

    TemporaryFileManagerService manager = new TemporaryFileManagerService();

    assertThrows(
        ErrorWhenTriedToDeleteTmpFile.class,
        () -> {
          manager.delete(tempFilePath);
        });
  }

  @Test
  void delete_throwsErrorWhenPathIsInvalid() {
    Path tempFilePath = tempDir.resolve("\0invalidFile.txt");

    TemporaryFileManagerService manager = new TemporaryFileManagerService();

    assertThrows(
        ErrorWhenTriedToDeleteTmpFile.class,
        () -> {
          manager.delete(tempFilePath);
        });
  }
}
