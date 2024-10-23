package com.autocrud.main.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException(String message) {
        super(message);
    }

    public EmailAlreadyUsedException() {
        super("Email already in use");
    }
}
