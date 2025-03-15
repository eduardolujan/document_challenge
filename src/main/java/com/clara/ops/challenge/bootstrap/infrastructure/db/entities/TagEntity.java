package com.clara.ops.challenge.bootstrap.infrastructure.db.entities;

import com.clara.ops.challenge.bootstrap.domain.db.DatabaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Tag")
@Table(name = "tags")
@Getter
@Setter
@Builder(toBuilder = true)
public class TagEntity implements DatabaseEntity {
  @Id private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "document_id")
  private UUID documentId;

  @ManyToOne
  @JoinColumn(insertable = false, updatable = false)
  private DocumentEntity document;

  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;

  @PrePersist
  private void prePersist() {
    setCreatedAt(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC").normalized()));
  }

  @PreUpdate
  private void preUpdate() {
    setUpdatedAt(OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC").normalized()));
  }
}
