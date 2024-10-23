package com.autocrud.main.exceptions;

public class FieldNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // Message par défaut
    private static final String DEFAULT_MESSAGE = "Field not found";

    public FieldNotFoundException(String message) {
        super(message);
    }

    public FieldNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
