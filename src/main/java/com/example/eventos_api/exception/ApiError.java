package com.example.eventos_api.exception;

import java.util.List;

public class ApiError {
    private final String message;
    private final List<String> errors;

    public ApiError(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
