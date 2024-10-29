package com.autocrud.main.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import com.autocrud.main.entities.CustomUserDetails;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceUnitTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private Authentication authentication;

    private SecretKey secretKey;

    private String username = "testUser";
    private Long userId = 1L;
    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        authService = new AuthService(secretKey);

        // Générer un token pour les tests
        token = Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 heure
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    @Test
    void testGenerateToken() {
        String generatedToken = authService.generateToken(username, userId);
        assertNotNull(generatedToken, "Le token généré ne doit pas être nul");
    }

    @Test
    void testGetUsernameFromToken() {
        String extractedUsername = authService.getUsernameFromToken(token);
        assertEquals(username, extractedUsername, "Le nom d'utilisateur extrait doit correspondre");
    }

    @Test
    void testGetUserIdFromToken() {
        Long extractedUserId = authService.getUserIdFromToken(token);
        assertEquals(userId, extractedUserId, "L'ID utilisateur extrait doit correspondre");
    }

    @Test
    void testIsTokenExpired() {
        assertFalse(authService.isTokenExpired(token), "Le token ne doit pas être expiré");
    }

    @Test
    void testValidateToken() {
        boolean isValid = authService.validateToken(token, username);
        assertTrue(isValid, "Le token doit être valide pour l'utilisateur correspondant");
    }

    @Test
    void testExtractUserId() {
        CustomUserDetails userDetails = new CustomUserDetails(userId, username, "password", null);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        Long extractedUserId = authService.extractUserId(authentication);
        assertEquals(userId, extractedUserId, "L'ID utilisateur extrait doit correspondre");
    }
}
