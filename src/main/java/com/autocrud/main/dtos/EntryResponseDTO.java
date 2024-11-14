package com.autocrud.main.dtos;

import java.time.LocalDateTime;

public class EntryResponseDTO {
    private Long id;
    private Long fieldId;
    private String value;
    private Long userId;
    private LocalDateTime createdAt;

    public EntryResponseDTO() {}

    public EntryResponseDTO(Long id, Long fieldId, String value, Long userId) {
        this.id = id;
        this.fieldId = fieldId;
        this.value = value;
        this.userId = userId;
    }

    public EntryResponseDTO(Long fieldId) {
        this.fieldId = fieldId;
    }

    public EntryResponseDTO(Long id, String value) {
        this.id = id;
        this.value = value;
    }


    public EntryResponseDTO(Long id, Long fieldId, String value) {
        this.id = id;
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

    public Long getuserId() {
        return userId;
    }

    public void setuserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
