package com.autocrud.main.exceptions;

public class FieldNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FieldNotFoundException(Long fieldId) {
        super("Field not found with ID: " + fieldId);
    }
}
