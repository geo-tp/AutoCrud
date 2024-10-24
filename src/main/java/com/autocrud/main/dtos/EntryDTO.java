package com.autocrud.main.dtos;

public class EntryDTO {
    private Long id;
    private Long fieldId;
    private String value;

    public EntryDTO() {}

    public EntryDTO(Long id, Long fieldId, String value) {
        this.id = id;
        this.fieldId = fieldId;
        this.value = value;
    }

    public EntryDTO(Long fieldId, String value) {
        this.fieldId = fieldId;
        this.value = value;
    }

    public Long getId() {
        return id;
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
