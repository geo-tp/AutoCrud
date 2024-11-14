package com.autocrud.main.services;

import com.autocrud.main.dtos.FieldResponseDTO;
import com.autocrud.main.dtos.UpdateFieldRequestDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.Field;
import com.autocrud.main.exceptions.custom.FieldNotFoundException;
import com.autocrud.main.repositories.FieldRepository;
import com.autocrud.main.transformers.FieldTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceUnitTest {

    @InjectMocks
    private FieldService fieldService;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FieldTransformer fieldTransformer;

    private Channel channel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        channel = new Channel();
        channel.setId(1L);
    }

    @Test
    void testCreateFieldsFromDTO() {
        FieldResponseDTO fieldDTO1 = new FieldResponseDTO("Field1", "String");
        FieldResponseDTO fieldDTO2 = new FieldResponseDTO("Field2", "Integer");
        List<FieldResponseDTO> fieldDTOs = Arrays.asList(fieldDTO1, fieldDTO2);

        Field field1 = new Field("Field1", "String", channel);
        Field field2 = new Field("Field2", "Integer", channel);
        List<Field> fields = Arrays.asList(field1, field2);

        when(fieldTransformer.convertToEntities(fieldDTOs, channel)).thenReturn(fields);
        when(fieldRepository.saveAll(fields)).thenReturn(fields);

        List<Field> result = fieldService.createFieldsFromDTO(fieldDTOs, channel);

        assertEquals(2, result.size());
        assertEquals("Field1", result.get(0).getFieldName());
        assertEquals("Integer", result.get(1).getDataType());
    }

    @Test
    void testGetFieldById_FieldExists() {
        Long fieldId = 1L;
        Field field = new Field("TestField", "String", channel);
        field.setId(fieldId);
        FieldResponseDTO fieldResponseDTO = new FieldResponseDTO("TestField", "String");

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
        when(fieldTransformer.convertToDTO(field)).thenReturn(fieldResponseDTO);

        FieldResponseDTO result = fieldService.getFieldById(fieldId);

        assertNotNull(result);
        assertEquals("TestField", result.getFieldName());
        assertEquals("String", result.getDataType());
    }

    @Test
    void testGetFieldById_FieldNotFound() {
        Long fieldId = 1L;
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        assertThrows(FieldNotFoundException.class, () -> fieldService.getFieldById(fieldId));
    }

    @Test
    void testUpdateFieldById_FieldExists() {
        Long fieldId = 1L;
        UpdateFieldRequestDTO fieldRequestDTO = new UpdateFieldRequestDTO();
        fieldRequestDTO.setFieldName("UpdateField");
        fieldRequestDTO.setType("Integer");

        FieldResponseDTO fieldResponseDTO = new FieldResponseDTO("UpdatedField", "Integer");
        Field field = new Field("OriginalField", "String", channel);
        field.setId(fieldId);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
        when(fieldRepository.save(field)).thenReturn(field);
        when(fieldTransformer.convertToDTO(field)).thenReturn(fieldResponseDTO);

        FieldResponseDTO result = fieldService.updateFieldById(fieldId, fieldRequestDTO);

        assertEquals("UpdatedField", result.getFieldName());
        assertEquals("Integer", result.getDataType());
    }

    @Test
    void testUpdateFieldById_FieldNotFound() {
        Long fieldId = 1L;
        UpdateFieldRequestDTO fieldRequestDTO = new UpdateFieldRequestDTO();
        fieldRequestDTO.setFieldName("UpdateField");
        fieldRequestDTO.setType("Integer");

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        assertThrows(FieldNotFoundException.class, () -> fieldService.updateFieldById(fieldId, fieldRequestDTO));
    }

    @Test
    void testGetChannelIdByFieldId_FieldExists() {
        Long fieldId = 1L;
        Field field = new Field("FieldName", "String", channel);
        field.setId(fieldId);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        Long result = fieldService.getChannelIdByFieldId(fieldId);

        assertEquals(channel.getId(), result);
    }

    @Test
    void testGetChannelIdByFieldId_FieldNotFound() {
        Long fieldId = 1L;

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        assertThrows(FieldNotFoundException.class, () -> fieldService.getChannelIdByFieldId(fieldId));
    }
}
