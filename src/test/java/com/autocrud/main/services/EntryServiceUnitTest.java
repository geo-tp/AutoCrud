package com.autocrud.main.services;

import com.autocrud.main.dtos.EntryDTO;
import com.autocrud.main.entities.Entry;
import com.autocrud.main.entities.Field;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.exceptions.custom.EntryNotFoundException;
import com.autocrud.main.exceptions.custom.FieldNotFoundException;
import com.autocrud.main.repositories.EntryRepository;
import com.autocrud.main.repositories.FieldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntryServiceUnitTest {

    @InjectMocks
    private EntryService entryService;

    @Mock
    private EntryRepository entryRepository;

    @Mock
    private FieldRepository fieldRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEntryFromDTO_FieldExists() {
        Long fieldId = 1L;
        String value = "Test Value";
        EntryDTO entryDTO = new EntryDTO(fieldId, value);
        Field field = new Field();
        field.setId(fieldId);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        Entry createdEntry = new Entry(value, field);
        when(entryRepository.save(any(Entry.class))).thenReturn(createdEntry);

        Entry result = entryService.createEntryFromDTO(entryDTO);

        assertNotNull(result);
        assertEquals(value, result.getValue());
        assertEquals(fieldId, result.getField().getId());
    }

    @Test
    void testCreateEntryFromDTO_FieldNotFound() {
        Long fieldId = 1L;
        EntryDTO entryDTO = new EntryDTO(fieldId, "Test Value");

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        assertThrows(FieldNotFoundException.class, () -> entryService.createEntryFromDTO(entryDTO));
    }

    @Test
    void testGetEntryById_EntryExists() {
        Long entryId = 1L;
        Field field = new Field();
        field.setId(1L);
        Entry entry = new Entry("Test Value", field);
        entry.setId(entryId);

        when(entryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        EntryDTO result = entryService.getEntryById(entryId);

        assertNotNull(result);
        assertEquals(entry.getValue(), result.getValue());
        assertEquals(entry.getField().getId(), result.getFieldId());
    }

    @Test
    void testGetEntryById_EntryNotFound() {
        Long entryId = 1L;

        when(entryRepository.findById(entryId)).thenReturn(Optional.empty());

        assertThrows(EntryNotFoundException.class, () -> entryService.getEntryById(entryId));
    }

    @Test
    void testUpdateEntry_EntryExists() {
        Long entryId = 1L;
        EntryDTO entryDTO = new EntryDTO(1L, "Updated Value");
        Field field = new Field();
        Entry entry = new Entry("Old Value", field);
        entry.setId(entryId);

        when(entryRepository.findById(entryId)).thenReturn(Optional.of(entry));
        when(entryRepository.save(any(Entry.class))).thenReturn(entry);

        EntryDTO result = entryService.updateEntry(entryId, entryDTO);

        assertEquals("Updated Value", result.getValue());
        verify(entryRepository, times(1)).save(entry);
    }

    @Test
    void testUpdateEntry_EntryNotFound() {
        Long entryId = 1L;
        EntryDTO entryDTO = new EntryDTO(1L, "Updated Value");

        when(entryRepository.findById(entryId)).thenReturn(Optional.empty());

        assertThrows(EntryNotFoundException.class, () -> entryService.updateEntry(entryId, entryDTO));
    }

    @Test
    void testDeleteEntry_EntryExists() {
        Long entryId = 1L;
        Entry entry = new Entry("Test Value", new Field());
        entry.setId(entryId);

        when(entryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        entryService.deleteEntry(entryId);

        verify(entryRepository, times(1)).delete(entry);
    }

    @Test
    void testDeleteEntry_EntryNotFound() {
        Long entryId = 1L;

        when(entryRepository.findById(entryId)).thenReturn(Optional.empty());

        assertThrows(EntryNotFoundException.class, () -> entryService.deleteEntry(entryId));
    }

    @Test
    void testGetChannelIdByEntryId_EntryExists() {
        Long entryId = 1L;
        Long channelId = 2L;
        Field field = new Field();
        Channel channel = new Channel();
        channel.setId(channelId);
        field.setChannel(channel);
        Entry entry = new Entry("Test Value", field);
        entry.setId(entryId);

        when(entryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        Long result = entryService.getChannelIdByEntryId(entryId);

        assertEquals(channelId, result);
    }
}
