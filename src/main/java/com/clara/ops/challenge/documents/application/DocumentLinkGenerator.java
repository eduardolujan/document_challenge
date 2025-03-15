package com.clara.ops.challenge.documents.application;

import com.clara.ops.challenge.documents.domain.entity.Document;
import com.clara.ops.challenge.documents.domain.exceptions.DocumentNotExists;
import com.clara.ops.challenge.documents.domain.repository.DocumentRepository;
import com.clara.ops.challenge.documents.domain.s3.LinkManager;
import java.util.Optional;
import java.util.UUID;

public class DocumentLinkGenerator {

  private final DocumentRepository documentRepository;
  private final LinkManager linkManager;

  public DocumentLinkGenerator(DocumentRepository documentRepository, LinkManager linkManager) {
    this.documentRepository = documentRepository;
    this.linkManager = linkManager;
  }

  public String generateLink(UUID id, String bucketName) throws DocumentNotExists {
    Optional<Document> document = documentRepository.findById(id);
    if (document.isEmpty()) {
      throw new DocumentNotExists("Document not found");
    }
    String path = "%s/%s".formatted(document.get().getUser(), document.get().getDocumentName());
    return linkManager.generateLink(bucketName, path);
  }
}
