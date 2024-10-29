package com.autocrud.main.transformers;

import com.autocrud.main.dtos.FieldDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldTransformerTest {

    @InjectMocks
    private FieldTransformer fieldTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertToDTO() {
        Field field = new Field();
        field.setId(1L);
        field.setFieldName("FieldName");
        field.setDataType("String");

        FieldDTO fieldDTO = fieldTransformer.convertToDTO(field);

        assertEquals(field.getId(), fieldDTO.getId());
        assertEquals(field.getFieldName(), fieldDTO.getFieldName());
        assertEquals(field.getDataType(), fieldDTO.getDataType());
    }

    @Test
    void testConvertToDTOs() {
        Field field1 = new Field();
        field1.setId(1L);
        field1.setFieldName("FieldName1");
        field1.setDataType("String");

        Field field2 = new Field();
        field2.setId(2L);
        field2.setFieldName("FieldName2");
        field2.setDataType("Integer");

        List<FieldDTO> fieldDTOs = fieldTransformer.convertToDTOs(Arrays.asList(field1, field2));

        assertEquals(2, fieldDTOs.size());
        assertEquals("FieldName1", fieldDTOs.get(0).getFieldName());
        assertEquals("FieldName2", fieldDTOs.get(1).getFieldName());
    }

    @Test
    void testConvertToEntity() {
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setId(1L);
        fieldDTO.setFieldName("FieldName");
        fieldDTO.setDataType("String");

        Field field = fieldTransformer.convertToEntity(fieldDTO);

        assertEquals(fieldDTO.getId(), field.getId());
        assertEquals(fieldDTO.getFieldName(), field.getFieldName());
        assertEquals(fieldDTO.getDataType(), field.getDataType());
    }

    @Test
    void testConvertToEntities() {
        FieldDTO fieldDTO1 = new FieldDTO();
        fieldDTO1.setId(1L);
        fieldDTO1.setFieldName("FieldName1");
        fieldDTO1.setDataType("String");

        FieldDTO fieldDTO2 = new FieldDTO();
        fieldDTO2.setId(2L);
        fieldDTO2.setFieldName("FieldName2");
        fieldDTO2.setDataType("Integer");

        Channel channel = new Channel();
        channel.setId(100L);

        List<Field> fields = fieldTransformer.convertToEntities(Arrays.asList(fieldDTO1, fieldDTO2), channel);

        assertEquals(2, fields.size());
        assertEquals("FieldName1", fields.get(0).getFieldName());
        assertEquals(channel, fields.get(0).getChannel());
        assertEquals("FieldName2", fields.get(1).getFieldName());
        assertEquals(channel, fields.get(1).getChannel());
    }
}
