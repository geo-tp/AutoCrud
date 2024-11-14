package com.autocrud.main.dtos;

public class CreateEntryRequestDTO {
    private Long id;
    private Long fieldId;
    private String value;

    public CreateEntryRequestDTO(Long id, Long fieldId, String value) {
        this.id = id;
        this.fieldId = fieldId;
        this.value = value;
    }

    public CreateEntryRequestDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
