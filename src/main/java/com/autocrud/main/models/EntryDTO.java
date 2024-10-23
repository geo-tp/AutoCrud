package com.autocrud.main.models;

public class EntryDTO {
    private Long fieldId;
    private String value;

    public EntryDTO(Long fieldId, String value) {
        this.fieldId = fieldId;
        this.value = value;
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
