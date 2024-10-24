package com.autocrud.main.transformers;

import com.autocrud.main.dtos.EntryDTO;
import com.autocrud.main.entities.Entry;
import org.springframework.stereotype.Component;

@Component
public class EntryTransformer {

    // Convert Entry entity to EntryDTO
    public EntryDTO convertToDTO(Entry entry) {
        return new EntryDTO(entry.getId(), entry.getField().getId(), entry.getValue());
    }

    // Convert EntryDTO to Entry entity
    public Entry convertToEntity(EntryDTO entryDTO, Entry entry) {
        entry.setValue(entryDTO.getValue());
        return entry;
    }
}
