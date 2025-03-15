package com.clara.ops.challenge.documents.domain.exceptions;

public class ErrorWhenTriedToCreateTmpFile extends Exception {
  public ErrorWhenTriedToCreateTmpFile(String message) {
    super(message);
  }
}
