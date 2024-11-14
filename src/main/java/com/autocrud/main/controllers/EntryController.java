package com.autocrud.main.controllers;

import com.autocrud.main.annotations.CheckOwnership;
import com.autocrud.main.dtos.CreateEntryRequestDTO;
import com.autocrud.main.dtos.EntryResponseDTO;
import com.autocrud.main.dtos.UpdateEntryRequestDTO;
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
    public List<EntryResponseDTO> addEntries(@RequestBody List<CreateEntryRequestDTO> entryDTOs) {
        return entryService.addEntriesFromDTO(entryDTOs);
    }

    // Get entry by ID
    @CheckOwnership(resourceId = "id", resourceType = "entry")
    @GetMapping("/{entryId}")
    public EntryResponseDTO getEntryById(@PathVariable Long entryId) {
        return entryService.getEntryById(entryId);
    }

    // Update entry
    @CheckOwnership(resourceId = "id", resourceType = "entry")
    @PutMapping("/{entryId}")
    public EntryResponseDTO updateEntry(@PathVariable Long entryId, @RequestBody UpdateEntryRequestDTO entryDTO) {
        return entryService.updateEntry(entryId, entryDTO);
    }

    // Delete entry by ID
    @CheckOwnership(resourceId = "id", resourceType = "entry")
    @DeleteMapping("/{entryId}")
    public void deleteEntry(@PathVariable Long entryId) {
        entryService.deleteEntry(entryId);
    }
}
