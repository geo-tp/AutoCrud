package com.autocrud.main.controllers;

import com.autocrud.main.dtos.CreateEntryRequestDTO;
import com.autocrud.main.dtos.EntryResponseDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.Entry;
import com.autocrud.main.entities.Field;
import com.autocrud.main.entities.User;
import com.autocrud.main.repositories.ChannelRepository;
import com.autocrud.main.repositories.EntryRepository;
import com.autocrud.main.repositories.FieldRepository;
import com.autocrud.main.repositories.UserRepository;
import com.autocrud.main.services.EntryService;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EntryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntryService entryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private EntryRepository entryRepository;

    private Long testUserId;

    private Field field1;
    private Field field2;
    private Entry defaultEntry;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        fieldRepository.deleteAll();
        channelRepository.deleteAll();
        entryRepository.deleteAll();

        User user = new User("testuser@example.com", "password");
        testUserId = userRepository.save(user).getId();

        Channel defaultChannel = new Channel("Default Channel", user);
        defaultChannel = channelRepository.save(defaultChannel);

        field1 = new Field();
        field1.setFieldName("field1");
        field1.setChannel(defaultChannel);
        field1 = fieldRepository.save(field1);

        field2 = new Field();
        field2.setFieldName("field2");
        field2.setChannel(defaultChannel);
        field2 = fieldRepository.save(field2);
        
        defaultEntry = new Entry();
        defaultEntry.setField(field1);
        defaultEntry.setValue("value1");
        defaultEntry = entryRepository.save(defaultEntry);
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testAddEntries() throws Exception {
        List<CreateEntryRequestDTO> entryDTOs = new ArrayList<>();
        entryDTOs.add(new CreateEntryRequestDTO(1L, field1.getId(), "value1"));
        entryDTOs.add(new CreateEntryRequestDTO(2L, field2.getId(), "value2"));

        mockMvc.perform(post("/api/entries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entryDTOs)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fieldId").value(field1.getId()))
                .andExpect(jsonPath("$[0].value").value("value1"))
                .andExpect(jsonPath("$[1].fieldId").value(field2.getId()))
                .andExpect(jsonPath("$[1].value").value("value2"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testGetEntryById() throws Exception {
        mockMvc.perform(get("/api/entries/" + defaultEntry.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fieldId").value(field1.getId()))
                .andExpect(jsonPath("$.value").value("value1"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testUpdateEntry() throws Exception {
        CreateEntryRequestDTO entryDTO = new CreateEntryRequestDTO();
        entryDTO.setFieldId((field1.getId()));
        entryDTO.setValue("value1");

        EntryResponseDTO savedEntry = entryService.addEntriesFromDTO(List.of(entryDTO)).get(0);

        EntryResponseDTO updatedEntryDTO = new EntryResponseDTO(null, field1.getId(), "newValue");

        mockMvc.perform(put("/api/entries/" + savedEntry.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEntryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("newValue"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = {"USER"})
    void testDeleteEntry() throws Exception {
        CreateEntryRequestDTO entryDTO = new CreateEntryRequestDTO();
        entryDTO.setFieldId((field1.getId()));
        entryDTO.setValue("value1");

        EntryResponseDTO savedEntry = entryService.addEntriesFromDTO(List.of(entryDTO)).get(0);

        mockMvc.perform(delete("/api/entries/" + savedEntry.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/entries/" + savedEntry.getId()))
                .andExpect(status().isNotFound());
    }
}
