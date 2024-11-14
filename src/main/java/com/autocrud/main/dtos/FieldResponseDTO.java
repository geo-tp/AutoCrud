package com.autocrud.main.dtos;

public class FieldResponseDTO {
    private Long id;
    private String fieldName;
    private String dataType;

    public FieldResponseDTO() {}

    public FieldResponseDTO(String fieldName, String dataType) {
        this.fieldName = fieldName;
        this.dataType = dataType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
