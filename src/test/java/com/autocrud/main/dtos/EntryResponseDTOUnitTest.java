package com.autocrud.main.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntryResponseDTOUnitTest {

    @Test
    public void testAllArgsConstructor() {
        LocalDateTime createdAt = LocalDateTime.now();
        EntryResponseDTO dto = new EntryResponseDTO(1L, 2L, "Test Value", 3L);
        dto.setCreatedAt(createdAt);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getFieldId());
        assertEquals("Test Value", dto.getValue());
        assertEquals(3L, dto.getUserId());
        assertEquals(createdAt, dto.getCreatedAt());
    }

    @Test
    public void testFieldIdOnlyConstructor() {
        EntryResponseDTO dto = new EntryResponseDTO(2L);

        assertEquals(2L, dto.getFieldId());
    }

    @Test
    public void testIdAndValueConstructor() {
        EntryResponseDTO dto = new EntryResponseDTO(1L, "Test Value");

        assertEquals(1L, dto.getId());
        assertEquals("Test Value", dto.getValue());
    }

    @Test
    public void testIdFieldIdAndValueConstructor() {
        EntryResponseDTO dto = new EntryResponseDTO(1L, 2L, "Test Value");

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getFieldId());
        assertEquals("Test Value", dto.getValue());
    }

    @Test
    public void testSettersAndGetters() {
        EntryResponseDTO dto = new EntryResponseDTO();
        LocalDateTime createdAt = LocalDateTime.now();

        dto.setId(1L);
        dto.setFieldId(2L);
        dto.setValue("Test Value");
        dto.setUserId(3L);
        dto.setCreatedAt(createdAt);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getFieldId());
        assertEquals("Test Value", dto.getValue());
        assertEquals(3L, dto.getUserId());
        assertEquals(createdAt, dto.getCreatedAt());
    }
}
