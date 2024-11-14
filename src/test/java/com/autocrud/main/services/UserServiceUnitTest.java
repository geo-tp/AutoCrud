package com.autocrud.main.services;

import com.autocrud.main.dtos.UserResponseDTO;
import com.autocrud.main.entities.User;
import com.autocrud.main.exceptions.custom.EmailAlreadyUsedException;
import com.autocrud.main.exceptions.custom.PasswordTooShortException;
import com.autocrud.main.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceUnitTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_Successful() {
        String email = "test@example.com";
        String password = "securePassword";
        String encodedPassword = "encodedPassword";
        List<String> roles = Arrays.asList("ROLE_USER");

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        UserResponseDTO userDTO = userService.createUser(email, password, roles);

        assertEquals(email, userDTO.getEmail());
        assertEquals(roles, userDTO.getRoles());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_EmailAlreadyUsed() {
        String email = "test@example.com";
        String password = "securePassword";
        List<String> roles = Arrays.asList("ROLE_USER");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyUsedException.class, () -> userService.createUser(email, password, roles));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_PasswordTooShort() {
        String email = "test@example.com";
        String password = "short";
        List<String> roles = Arrays.asList("ROLE_USER");

        assertThrows(PasswordTooShortException.class, () -> userService.createUser(email, password, roles));
        verify(userRepository, never()).save(any(User.class));
    }
}
