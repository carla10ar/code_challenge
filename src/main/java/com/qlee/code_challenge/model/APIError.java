package com.qlee.code_challenge.model;

public class APIError {

    private String message;

    public APIError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
