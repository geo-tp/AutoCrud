package com.autocrud.main.exceptions;

public class ChannelNameAlreadyUsedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ChannelNameAlreadyUsedException(String channelName) {
        super("Channel name '" + channelName + "' is already in use");
    }

    public ChannelNameAlreadyUsedException() {
        super("Channel name is already in use");
    }
}
