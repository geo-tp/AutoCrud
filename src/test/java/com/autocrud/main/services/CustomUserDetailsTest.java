package com.autocrud.main.services;

import com.autocrud.main.entities.CustomUserDetails;
import com.autocrud.main.entities.User;
import com.autocrud.main.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        // Préparation des données de test
        Long userId = 1L;
        String email = "test@example.com";
        String password = "password123";
        User user = new User();
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);

        // Comportement des mocks
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Exécution du service
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Vérifications
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof CustomUserDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(userId, ((CustomUserDetails) userDetails).getUserId());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Vérifie que l'exception UsernameNotFoundException est bien levée
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(email));
    }
}
