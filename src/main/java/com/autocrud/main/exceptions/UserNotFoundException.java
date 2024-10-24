package com.autocrud.main.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long userId) {
        super("User not found for ID: " + userId);
    }

    public UserNotFoundException() {
        super("User not found");
    }
}
