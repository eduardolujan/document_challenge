package com.clara.ops.challenge.documents.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements ResponseChallenge {
  private String message;
}
