package com.autocrud.main;

import com.autocrud.main.config.SecurityConfig;
import com.autocrud.main.models.UserDTO;
import com.autocrud.main.services.UserService;
import com.autocrud.main.controllers.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserDTO userDTO = new UserDTO("test@example.com", List.of("ROLE_USER"));
        Mockito.when(userService.createUser(anyString(), anyString(), anyList()))
               .thenReturn(userDTO);
    }

    @Test
    void createUser_Successful() throws Exception {
        mockMvc.perform(post("/api/users/create")
                .param("email", "test@example.com")
                .param("password", "password123")
                .param("roles", "ROLE_USER")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }
}
