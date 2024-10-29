package com.autocrud.main.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.autocrud.main.exceptions.custom.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

class GlobalExceptionHandlerUnitTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleChannelNotFoundException() {
        ChannelNotFoundException ex = new ChannelNotFoundException(1L);
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleChannelNotFoundException(ex);
        assertEquals("No channel found with ID: 1", response.getBody().get("error"));
    }

    @Test
    void handleEmailAlreadyUsedException() {
        EmailAlreadyUsedException ex = new EmailAlreadyUsedException();
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleEmailAlreadyUsedException(ex);
        assertEquals("Email already in use", response.getBody().get("error"));
    }

    @Test
    void handlePasswordTooShortException() {
        PasswordTooShortException ex = new PasswordTooShortException();
        ResponseEntity<Map<String, String>> response = exceptionHandler.handlePasswordTooShortException(ex);
        assertEquals("Password must be at least 8 characters long", response.getBody().get("error"));
    }

    @Test
    void handleChannelNameAlreadyUsedException() {
        ChannelNameAlreadyUsedException ex = new ChannelNameAlreadyUsedException();
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleChannelNameAlreadyUsedException(ex);
        assertEquals("Channel name is already in use", response.getBody().get("error"));
    }

    @Test
    void handleUserNotFoundException() {
        UserNotFoundException ex = new UserNotFoundException(1L);
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleUserNotFoundException(ex);
        assertEquals("User not found for ID: 1", response.getBody().get("error"));
    }
}
