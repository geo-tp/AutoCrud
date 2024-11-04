package com.autocrud.main.controllers;

import com.autocrud.main.dtos.EntryDTO;
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
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
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
    private String jwtToken;
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

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
        
        // Generer un token JWT pour l'utilisateur de test
        jwtToken = Jwts.builder()
            .setSubject("testuser@example.com")
            .claim("userId", testUserId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();
    }

    @Test
    void testAddEntries() throws Exception {
        List<EntryDTO> entryDTOs = new ArrayList<>();
        entryDTOs.add(new EntryDTO(1L, field1.getId(), "value1"));
        entryDTOs.add(new EntryDTO(2L, field2.getId(), "value2"));

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
    void testGetEntryById() throws Exception {
        mockMvc.perform(get("/api/entries/" + defaultEntry.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fieldId").value(field1.getId()))
                .andExpect(jsonPath("$.value").value("value1"));
    }

    @Test
    void testUpdateEntry() throws Exception {
        EntryDTO entryDTO = new EntryDTO(null, field1.getId(), "value1");
        EntryDTO savedEntry = entryService.addEntriesFromDTO(List.of(entryDTO)).get(0);

        EntryDTO updatedEntryDTO = new EntryDTO(null, field1.getId(), "newValue");

        mockMvc.perform(put("/api/entries/" + savedEntry.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEntryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("newValue"));
    }

    @Test
    void testDeleteEntry() throws Exception {
        EntryDTO entryDTO = new EntryDTO(null, field1.getId(), "value1");
        EntryDTO savedEntry = entryService.addEntriesFromDTO(List.of(entryDTO)).get(0);

        mockMvc.perform(delete("/api/entries/" + savedEntry.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/entries/" + savedEntry.getId()))
                .andExpect(status().isNotFound());
    }
}
