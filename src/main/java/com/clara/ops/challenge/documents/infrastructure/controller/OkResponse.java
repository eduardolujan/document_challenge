package com.clara.ops.challenge.documents.infrastructure.controller;




public class OkResponse implements ResponseChallenge {

    public OkResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    private Boolean success;
    private String message;

    public OkResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
