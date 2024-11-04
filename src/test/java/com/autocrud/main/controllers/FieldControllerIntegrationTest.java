package com.autocrud.main.controllers;

import com.autocrud.main.dtos.FieldDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.Field;
import com.autocrud.main.entities.User;
import com.autocrud.main.repositories.ChannelRepository;
import com.autocrud.main.repositories.FieldRepository;
import com.autocrud.main.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FieldControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Field testField;

    @BeforeEach
    void setup() {
        fieldRepository.deleteAll();
        channelRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User("testuser@example.com", "password");
        userRepository.save(user);
        
        Channel channel = new Channel("Test Channel", user);
        channel = channelRepository.save(channel);

        Field field = new Field();
        field.setFieldName("Test Field");
        field.setChannel(channel);
        testField = fieldRepository.save(field);
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testGetFieldById() throws Exception {
        mockMvc.perform(get("/api/fields/" + testField.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fieldName").value("Test Field"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testUpdateField() throws Exception {
        FieldDTO updatedFieldDTO = new FieldDTO();
        updatedFieldDTO.setFieldName("Updated Field Name");

        mockMvc.perform(put("/api/fields/" + testField.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFieldDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fieldName").value("Updated Field Name"));
    }
}
