package com.autocrud.main.controllers;

import com.autocrud.main.annotations.CheckOwnership;
import com.autocrud.main.dtos.EntryDTO;
import com.autocrud.main.services.EntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    // Add entries
    @PostMapping
    public List<EntryDTO> addEntries(@RequestBody List<EntryDTO> entryDTOs) {
        return entryService.addEntriesFromDTO(entryDTOs);
    }

    // Get entry by ID
    @CheckOwnership(resourceId = "id", resourceType = "entry")
    @GetMapping("/{entryId}")
    public EntryDTO getEntryById(@PathVariable Long entryId) {
        return entryService.getEntryById(entryId);
    }

    // Update entry
    @CheckOwnership(resourceId = "id", resourceType = "entry")
    @PutMapping("/{entryId}")
    public EntryDTO updateEntry(@PathVariable Long entryId, @RequestBody EntryDTO entryDTO) {
        return entryService.updateEntry(entryId, entryDTO);
    }

    // Delete entry by ID
    @CheckOwnership(resourceId = "id", resourceType = "entry")
    @DeleteMapping("/{entryId}")
    public void deleteEntry(@PathVariable Long entryId) {
        entryService.deleteEntry(entryId);
    }
}
