package com.autocrud.main.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldResponseDTO {
    private Long id;
    private String fieldName;
    private String dataType;

    public FieldResponseDTO(String fieldName, String dataType) {
        this.fieldName = fieldName;
        this.dataType = dataType;
    }
}
