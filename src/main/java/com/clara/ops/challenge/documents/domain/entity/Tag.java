package com.clara.ops.challenge.documents.domain.entity;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tag implements Entity {
  private UUID id;
  private String name;
  private UUID documentId;
}
