package com.autocrud.main.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EntryResponseDTO {
    private Long id;
    private Long fieldId;
    private String value;
    private Long userId;
    private LocalDateTime createdAt;

    // Custom constructors as needed
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
}
