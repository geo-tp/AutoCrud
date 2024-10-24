package com.autocrud.main.services;

import com.autocrud.main.dtos.FieldDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.Field;
import com.autocrud.main.exceptions.FieldNotFoundException;
import com.autocrud.main.repositories.FieldRepository;
import com.autocrud.main.transformers.FieldTransformer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FieldTransformer fieldTransformer;

    public FieldService(FieldRepository fieldRepository, FieldTransformer fieldTransformer) {
        this.fieldRepository = fieldRepository;
        this.fieldTransformer = fieldTransformer;
    }

    public List<Field> createFieldsFromDTO(List<FieldDTO> fieldDTOs, Channel channel) {
        return fieldRepository.saveAll(fieldTransformer.convertToEntities(fieldDTOs, channel));
    }

    public FieldDTO getFieldById(Long fieldId) {
        Field field = fieldRepository.findById(fieldId)
            .orElseThrow(() -> new FieldNotFoundException(fieldId));

        return fieldTransformer.convertToDTO(field);
    }

    public FieldDTO updateFieldById(Long fieldId, FieldDTO fieldDTO) {
        Field field = fieldRepository.findById(fieldId)
            .orElseThrow(() -> new FieldNotFoundException(fieldId));

        field.setFieldName(fieldDTO.getFieldName());
        field.setDataType(fieldDTO.getDataType());

        Field updatedField = fieldRepository.save(field);
        return fieldTransformer.convertToDTO(updatedField);
    }
}
