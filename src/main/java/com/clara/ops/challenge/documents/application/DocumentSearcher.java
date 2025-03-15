package com.clara.ops.challenge.documents.application;


import java.util.List;

// Domain
import com.clara.ops.challenge.documents.domain.entity.Document;
import com.clara.ops.challenge.documents.domain.repository.DocumentRepository;

public class DocumentSearcher {

  private final DocumentRepository documentRepository;

  public DocumentSearcher(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

    public List<Document> search(
        String user,
        String documentName,
        List<String> tags,
        int page,
        int pageSize) {
        return documentRepository.search(user, documentName, tags, page, pageSize);
    }

}
