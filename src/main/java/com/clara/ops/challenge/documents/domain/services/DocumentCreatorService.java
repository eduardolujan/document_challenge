package com.clara.ops.challenge.documents.domain.services;

import com.clara.ops.challenge.documents.domain.exceptions.DocumentParamEmpty;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

// Domain
import com.clara.ops.challenge.documents.domain.repository.DocumentRepository;
import com.clara.ops.challenge.documents.domain.entity.Document;

public class DocumentCreatorService {

  public static Document createDocument(
      DocumentRepository documentRepository,
      String user,
      String documentName) throws DocumentParamEmpty {
    if(Objects.isNull(documentName) || documentName.isEmpty()) {
      throw new DocumentParamEmpty("Document name cannot be null or empty %s".formatted(documentName));
    }

    if(Objects.isNull(user) || user.isEmpty()) {
      throw new DocumentParamEmpty("User cannot be null or empty %s".formatted(user));
    }

    OffsetDateTime now = OffsetDateTime.now();
    Document document = Document.builder()
      .id(UUID.randomUUID())
      .user(user)
      .documentName(documentName)
      .createdAt(now)
      .updatedAt(now)
      .build();
    documentRepository.save(document);
    return document;
  }

}
