package com.autocrud.main.transformers;

import com.autocrud.main.dtos.EntryResponseDTO;
import com.autocrud.main.entities.Entry;
import org.springframework.stereotype.Component;

@Component
public class EntryTransformer {

    // Convert Entry entity to EntryDTO
    public EntryResponseDTO convertToDTO(Entry entry) {
        return new EntryResponseDTO(entry.getId(), entry.getField().getId(), entry.getValue());
    }

    // Convert EntryDTO to Entry entity
    public Entry convertToEntity(EntryResponseDTO entryDTO, Entry entry) {
        entry.setValue(entryDTO.getValue());
        return entry;
    }
}
