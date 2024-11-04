package com.autocrud.main.controllers;

import com.autocrud.main.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testCreateUserWithRoles() throws Exception {
        String email = "newuser@example.com";
        String password = "password";
        String rolesParam = "ROLE_USER,ROLE_ADMIN";

        mockMvc.perform(post("/api/users/create")
                .param("email", email)
                .param("password", password)
                .param("roles", rolesParam))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"))
                .andExpect(jsonPath("$.roles[1]").value("ROLE_ADMIN"));
    }

    @Test
    void testCreateUserWithoutRoles() throws Exception {
        String email = "basicuser@example.com";
        String password = "password";

        mockMvc.perform(post("/api/users/create")
                .param("email", email)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));  // Default role
    }
}
