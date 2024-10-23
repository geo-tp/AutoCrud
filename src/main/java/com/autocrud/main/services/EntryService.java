package com.autocrud.main.services;

import com.autocrud.main.models.EntryDTO;
import com.autocrud.main.exceptions.FieldNotFoundException;
import com.autocrud.main.models.Entry;
import com.autocrud.main.models.Field;
import com.autocrud.main.repositories.EntryRepository;
import com.autocrud.main.repositories.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Service
public class EntryService {

    private final EntryRepository entryRepository;
    private final FieldRepository fieldRepository;

    public EntryService(EntryRepository entryRepository, FieldRepository fieldRepository) {
        this.entryRepository = entryRepository;
        this.fieldRepository = fieldRepository;
    }

    public Entry createEntryFromDTO(EntryDTO entryDTO) {
        Optional<Field> fieldOptional = fieldRepository.findById(entryDTO.getFieldId());
        if (!fieldOptional.isPresent()) {
            throw new FieldNotFoundException("Field not found for id: " + entryDTO.getFieldId());
        }
        Field field = fieldOptional.get();
        Entry entry = new Entry(entryDTO.getValue(), field);
        return entryRepository.save(entry);
    }

    public EntryDTO convertToDTO(Entry entry) {
        return new EntryDTO(entry.getField().getId(), entry.getValue());
    }

    public List<EntryDTO> addEntriesFromDTO(List<EntryDTO> entryDTOs) {
        List<EntryDTO> createdEntries = new ArrayList<>();
        for (EntryDTO entryDTO : entryDTOs) {
            Entry createdEntry = createEntryFromDTO(entryDTO);
            createdEntries.add(convertToDTO(createdEntry));
        }
        return createdEntries;
    }
}
