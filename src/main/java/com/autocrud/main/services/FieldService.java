package com.autocrud.main.services;

import com.autocrud.main.models.Field;
import com.autocrud.main.models.Channel;
import com.autocrud.main.models.FieldDTO;
import com.autocrud.main.repositories.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public List<Field> createFieldsFromDTO(List<FieldDTO> fieldDTOs, Channel channel) {
        List<Field> fields = new ArrayList<>();
        for (FieldDTO fieldDTO : fieldDTOs) {
            Field field = new Field();
            field.setFieldName(fieldDTO.getFieldName());
            field.setDataType(fieldDTO.getDataType());
            field.setChannel(channel);
            fields.add(field);
        }
        return fieldRepository.saveAll(fields);
    }

    public List<FieldDTO> convertFieldsToDTOs(List<Field> fields) {
        List<FieldDTO> fieldDTOs = new ArrayList<>();
        fields.forEach(field -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setId(field.getId());
            fieldDTO.setFieldName(field.getFieldName());
            fieldDTO.setDataType(field.getDataType());
            fieldDTOs.add(fieldDTO);
        });
        return fieldDTOs;
    }
}
