package com.autocrud.main.controllers;

import com.autocrud.main.config.SecurityConfig;
import com.autocrud.main.dtos.ChannelDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.User;
import com.autocrud.main.repositories.ChannelRepository;
import com.autocrud.main.transformers.ChannelTransformer;
import com.autocrud.main.transformers.FieldTransformer;
import com.autocrud.main.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc()
@ActiveProfiles("test")
public class ChannelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelTransformer channelTransformer;

    @Autowired
    private FieldTransformer fieldTransformer;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long testUserId;

    @BeforeEach
    void setup() {
        channelRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User("testuser@example.com", "password");
        testUserId = userRepository.save(user).getId();
    }

    @Test
    void testCreateChannel() throws Exception {
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setChannelName("New Channel");
        channelDTO.setOwnerId(testUserId);
        channelDTO.setFields(new ArrayList<>());

        mockMvc.perform(post("/api/channels/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(channelDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.channelName").value("New Channel"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testGetChannelById() throws Exception {
        Channel channel = new Channel("Test Channel", userRepository.findById(testUserId).get());
        channel = channelRepository.save(channel);

        mockMvc.perform(get("/api/channels/" + channel.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.channelName").value("Test Channel"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testGetChannelById_NotFound() throws Exception {
        mockMvc.perform(get("/api/channels/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No channel found with ID: 999"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testUpdateChannel() throws Exception {
        Channel channel = new Channel("Old Channel", userRepository.findById(testUserId).get());
        channel = channelRepository.save(channel);

        ChannelDTO channelDTO = channelTransformer.convertToDTO(channel);
        channelDTO.setChannelName("Updated Channel");
        channelDTO.setFields(fieldTransformer.convertToDTOs((channel.getFields())));

        mockMvc.perform(put("/api/channels/999")
                // .header("Authorization", "Bearer " + jwtToken) // authorization header
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(channelDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No channel found with ID: 999"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testUpdateChannel_NotFound() throws Exception {
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setChannelName("Non-existent Channel");
        channelDTO.setOwnerId(testUserId);

        mockMvc.perform(put("/api/channels/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(channelDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No channel found with ID: 999"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testDeleteChannel() throws Exception {
        Channel channel = new Channel("Channel to Delete", userRepository.findById(testUserId).get());
        channel = channelRepository.save(channel);

        mockMvc.perform(delete("/api/channels/" + channel.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.channelName").value("Channel to Delete"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testDeleteChannel_NotFound() throws Exception {
        mockMvc.perform(delete("/api/channels/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No channel found with ID: 999"));
    }
}
