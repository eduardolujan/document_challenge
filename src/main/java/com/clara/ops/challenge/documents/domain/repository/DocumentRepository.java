package com.clara.ops.challenge.documents.domain.repository;


// Domain
import com.clara.ops.challenge.documents.domain.entity.Document;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository {
  List<Document> search(String user, String documentName, List<String> tags, int page, int pageSize);
  Document save(Document document);

  Optional<Document> findById(UUID id);
}
