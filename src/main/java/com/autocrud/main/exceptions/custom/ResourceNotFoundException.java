package com.autocrud.main.exceptions.custom;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String ressourceId) {
        super("No resource found with ID: " + ressourceId);
    }

    public ResourceNotFoundException() {
        super("No ressource found");
    }
}
