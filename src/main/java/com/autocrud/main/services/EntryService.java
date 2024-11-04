package com.autocrud.main.services;

import com.autocrud.main.dtos.EntryDTO;
import com.autocrud.main.entities.Entry;
import com.autocrud.main.entities.Field;
import com.autocrud.main.exceptions.custom.EntryNotFoundException;
import com.autocrud.main.exceptions.custom.FieldNotFoundException;
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
            throw new FieldNotFoundException(entryDTO.getFieldId());
        }
        Field field = fieldOptional.get();
        Entry entry = new Entry(entryDTO.getValue(), field);
        return entryRepository.save(entry);
    }

    public EntryDTO convertToDTO(Entry entry) {
        return new EntryDTO(entry.getId(), entry.getField().getId(), entry.getValue());
    }    

    public List<EntryDTO> addEntriesFromDTO(List<EntryDTO> entryDTOs) {
        List<EntryDTO> createdEntries = new ArrayList<>();
        for (EntryDTO entryDTO : entryDTOs) {
            Entry createdEntry = createEntryFromDTO(entryDTO);
            createdEntries.add(convertToDTO(createdEntry));
        }
        return createdEntries;
    }

    // Get entry by ID
    public EntryDTO getEntryById(Long entryId) {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new EntryNotFoundException(entryId));
        return convertToDTO(entry);
    }

    // Update entry
    public EntryDTO updateEntry(Long entryId, EntryDTO entryDTO) {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new EntryNotFoundException(entryId));
        entry.setValue(entryDTO.getValue());
        Entry updatedEntry = entryRepository.save(entry);
        return convertToDTO(updatedEntry);
    }

    // Delete entry by ID
    public void deleteEntry(Long entryId) {
        Entry entry = entryRepository.findById(entryId)
            .orElseThrow(() -> new EntryNotFoundException(entryId));
        entryRepository.delete(entry);
    }

    // Get Channel ID
    public Long getChannelIdByEntryId(Long entryId) {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new EntryNotFoundException(entryId));
        return entry.getField().getChannel().getId();
    }
}
