package com.clara.ops.challenge.documents.domain.exceptions;

public class ErrorWhenTriedToDeleteTmpFile extends Exception {
    public ErrorWhenTriedToDeleteTmpFile(String message) {
        super(message);
    }
}
