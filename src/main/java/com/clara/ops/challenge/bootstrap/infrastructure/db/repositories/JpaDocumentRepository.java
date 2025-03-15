package com.clara.ops.challenge.bootstrap.infrastructure.db.repositories;

import com.clara.ops.challenge.bootstrap.infrastructure.db.entities.DocumentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDocumentRepository extends JpaRepository<DocumentEntity, UUID> {

  @Query(
      value =
          """
select distinct d.* from documents d join tags t
on d.id = t.document_id
where \"user\" = :user or document_name = :documentName or t.name in :tags;""",
      nativeQuery = true)
  List<DocumentEntity> searchByUserOrDocumentNameOrTagsIn(
      String user, String documentName, List<String> tags);
}
