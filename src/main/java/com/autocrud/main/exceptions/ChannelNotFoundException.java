package com.autocrud.main.exceptions;

public class ChannelNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ChannelNotFoundException(Long channelId) {
        super("No channel found with ID: " + channelId);
    }

    public ChannelNotFoundException() {
        super("No channel found");
    }
}
