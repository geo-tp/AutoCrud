package com.autocrud.main.transformers;

import com.autocrud.main.dtos.FieldDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.Field;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FieldTransformer {

    // Convert Field entity to FieldDTO
    public FieldDTO convertToDTO(Field field) {
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setId(field.getId());
        fieldDTO.setFieldName(field.getFieldName());
        fieldDTO.setDataType(field.getDataType());
        return fieldDTO;
    }

    // Convert a list of Field entities to a list of FieldDTOs
    public List<FieldDTO> convertToDTOs(List<Field> fields) {
        return fields.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Convert FieldDTO to Field entity
    public Field convertToEntity(FieldDTO fieldDTO) {
        Field field = new Field();
        field.setId(fieldDTO.getId());
        field.setFieldName(fieldDTO.getFieldName());
        field.setDataType(fieldDTO.getDataType());
        return field;
    }

    // Convert a list of FieldDTOs to a list of Field entities
    public List<Field> convertToEntities(List<FieldDTO> fieldDTOs, Channel channel) {
        return fieldDTOs.stream()
            .map(dto -> {
                Field field = convertToEntity(dto);
                field.setChannel(channel);
                return field;
            })
            .collect(Collectors.toList());
    }
}
