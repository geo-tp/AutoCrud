package com.autocrud.main.exceptions.custom;

public class EntryNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // Constructor to accept entry ID and include it in the exception message
    public EntryNotFoundException(Long entryId) {
        super("Entry not found with ID: " + entryId);
    }

    // Default constructor with a generic message
    public EntryNotFoundException() {
        super("Entry not found");
    }
}
