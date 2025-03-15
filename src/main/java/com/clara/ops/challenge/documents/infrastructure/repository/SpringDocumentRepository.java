package com.clara.ops.challenge.documents.infrastructure.repository;

import com.clara.ops.challenge.bootstrap.infrastructure.db.entities.DocumentEntity;
import com.clara.ops.challenge.bootstrap.infrastructure.db.repositories.JpaDocumentRepository;
import com.clara.ops.challenge.documents.domain.entity.Document;
import com.clara.ops.challenge.documents.domain.repository.DocumentRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SpringDocumentRepository implements DocumentRepository {

  private final JpaDocumentRepository jpaDocumentRepository;
  private final EntityModelMapper entityModelMapper;

  public SpringDocumentRepository(
      JpaDocumentRepository jpaDocumentRepository, EntityModelMapper entityModelMapper) {
    this.jpaDocumentRepository = jpaDocumentRepository;
    this.entityModelMapper = entityModelMapper;
  }

  @Override
  public List<Document> search(
      String user, String documentName, List<String> tags, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    var data = jpaDocumentRepository.searchByUserOrDocumentNameOrTagsIn(user, documentName, tags);
    return data.stream()
        .map(
            documentEntity ->
                entityModelMapper.getModelMapper().map(documentEntity, Document.class))
        .collect(Collectors.toList());
  }

  @Override
  public Document save(Document document) {
    jpaDocumentRepository.save(
        entityModelMapper.getModelMapper().map(document, DocumentEntity.class));
    return document;
  }

  @Override
  public Optional<Document> findById(UUID id) {
    return jpaDocumentRepository
        .findById(id)
        .map(
            documentEntity ->
                entityModelMapper.getModelMapper().map(documentEntity, Document.class));
  }
}
