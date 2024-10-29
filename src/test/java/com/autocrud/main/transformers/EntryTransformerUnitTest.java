package com.autocrud.main.transformers;

import com.autocrud.main.dtos.EntryDTO;
import com.autocrud.main.entities.Entry;
import com.autocrud.main.entities.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class EntryTransformerTest {

    @InjectMocks
    private EntryTransformer entryTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertToDTO() {
        Field field = new Field();
        field.setId(1L);

        Entry entry = new Entry();
        entry.setId(100L);
        entry.setField(field);
        entry.setValue("Test Value");

        EntryDTO entryDTO = entryTransformer.convertToDTO(entry);

        assertEquals(entry.getId(), entryDTO.getId());
        assertEquals(entry.getField().getId(), entryDTO.getFieldId());
        assertEquals(entry.getValue(), entryDTO.getValue());
    }

    @Test
    void testConvertToEntity() {
        EntryDTO entryDTO = new EntryDTO();
        entryDTO.setValue("Updated Value");

        Entry entry = new Entry();
        entry.setValue("Old Value");

        Entry updatedEntry = entryTransformer.convertToEntity(entryDTO, entry);

        assertEquals(entryDTO.getValue(), updatedEntry.getValue());
    }
}
