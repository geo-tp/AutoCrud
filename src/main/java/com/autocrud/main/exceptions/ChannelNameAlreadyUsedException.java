package com.autocrud.main.exceptions;

public class ChannelNameAlreadyUsedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // Message par défaut
    private static final String DEFAULT_MESSAGE = "Channel name is already in use";

    public ChannelNameAlreadyUsedException(String message) {
        super(message);
    }

    public ChannelNameAlreadyUsedException() {
        super(DEFAULT_MESSAGE);
    }
}
