package com.clara.ops.challenge.documents.domain.entity;


import jakarta.persistence.Column;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class Document implements Entity {
  private UUID id;

  private String user;

  @Column(name = "document_name")
  private String documentName;

  private List<Tag> tags;

  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

}
