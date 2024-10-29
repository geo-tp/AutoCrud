package com.autocrud.main.exceptions.custom;

public class PasswordTooShortException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PasswordTooShortException(String message) {
        super(message);
    }

    public PasswordTooShortException() {
        super("Password must be at least 8 characters long");
    }
}
