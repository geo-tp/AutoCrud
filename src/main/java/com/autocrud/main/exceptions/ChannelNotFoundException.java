package com.autocrud.main.exceptions;

public class ChannelNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    // Message par défaut
    private static final String DEFAULT_MESSAGE = "No channel found";


    public ChannelNotFoundException(String message) {
        super(message);
    }
    
    public ChannelNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}