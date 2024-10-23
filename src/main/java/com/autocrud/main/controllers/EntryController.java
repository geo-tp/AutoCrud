package com.autocrud.main.controllers;

import com.autocrud.main.models.EntryDTO;
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

    @PostMapping
    public List<EntryDTO> addEntries(@RequestBody List<EntryDTO> entryDTOs) {
        return entryService.addEntriesFromDTO(entryDTOs);
    }
}
