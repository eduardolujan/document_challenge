package com.clara.ops.challenge.documents.application;


import com.clara.ops.challenge.documents.domain.exceptions.DocumentParamEmpty;
import com.clara.ops.challenge.documents.domain.exceptions.TagNamesEmpty;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

// Domain
import com.clara.ops.challenge.documents.domain.entity.Document;
import com.clara.ops.challenge.documents.domain.repository.TagRepository;
import com.clara.ops.challenge.documents.domain.services.TagCreatorService;
import com.clara.ops.challenge.documents.domain.repository.DocumentRepository;
import com.clara.ops.challenge.documents.domain.services.DocumentCreatorService;
import com.clara.ops.challenge.documents.domain.s3.FileManager;
import com.clara.ops.challenge.documents.domain.services.FileUploaderService;
import com.clara.ops.challenge.documents.domain.exceptions.ErrorWhenTriedToDeleteTmpFile;
import com.clara.ops.challenge.documents.domain.exceptions.ErrorWhenTriedToCreateTmpFile;
import com.clara.ops.challenge.documents.domain.services.TemporaryFileManagerService;

public class DocumentCreator {

  private final DocumentRepository documentRepository;
  private final TagRepository tagRepository;
  private final String bucketName;
  private final String userName;
  private final String documentName;
  private final List<String> tags;
  private final InputStream userFile;
  private final FileManager fileManager;

  public DocumentCreator(
      FileManager fileManager,
      DocumentRepository documentRepository,
      TagRepository tagRepository,
      String bucketName,
      String username,
      String documentName,
      List<String> tags,
      InputStream userFile) {
    this.documentRepository = documentRepository;
    this.tagRepository = tagRepository;
    this.bucketName = bucketName;
    this.userName = username;
    this.documentName = documentName;
    this.tags = tags;
    this.userFile = userFile;
    this.fileManager = fileManager;

  }

  public void execute() throws
      ErrorWhenTriedToCreateTmpFile,
      ErrorWhenTriedToDeleteTmpFile,
      DocumentParamEmpty,
      TagNamesEmpty {

    Path tempFilePath = Path.of("/tmp/",
        String.format("%s_%s.tmp", userName, UUID.randomUUID().toString()));

    TemporaryFileManagerService.createTemporaryFile(tempFilePath, userFile);
    FileUploaderService.upload(fileManager, tempFilePath, bucketName, documentName, userName);
    TemporaryFileManagerService.delete(tempFilePath);
    Document document = DocumentCreatorService.createDocument(documentRepository, userName, documentName);
    TagCreatorService.createTags(tagRepository, document, tags);
  }

}
