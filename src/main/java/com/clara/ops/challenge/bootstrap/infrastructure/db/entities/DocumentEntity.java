package com.clara.ops.challenge.bootstrap.infrastructure.db.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Domain
import com.clara.ops.challenge.bootstrap.domain.db.DatabaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Document")
@Table(name = "documents")
@Builder(toBuilder = true)
public class DocumentEntity implements DatabaseEntity {
  @Id
  private UUID id;

  @Column(name = "\"user\"")
  private String user;

  @Column(name = "document_name")
  private String documentName;

  @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TagEntity> tags;

  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;

  @PrePersist
  private void prePersist(){
    setCreatedAt(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC").normalized()));
  }

  @PreUpdate
  private void preUpdate(){
    setUpdatedAt(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC").normalized()));
  }
}
