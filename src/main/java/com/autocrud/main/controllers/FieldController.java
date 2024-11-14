package com.autocrud.main.controllers;

import com.autocrud.main.annotations.CheckOwnership;
import com.autocrud.main.dtos.FieldResponseDTO;
import com.autocrud.main.dtos.UpdateFieldRequestDTO;
import com.autocrud.main.services.FieldService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fields")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    // Get field by ID
    @CheckOwnership(resourceId = "id", resourceType = "field")
    @GetMapping("/{fieldId}")
    public FieldResponseDTO getFieldById(@PathVariable Long fieldId) {
        return fieldService.getFieldById(fieldId);
    }

    // Update an existing field
    @CheckOwnership(resourceId = "id", resourceType = "field")
    @PutMapping("/{fieldId}")
    public FieldResponseDTO updateField(@PathVariable Long fieldId, @RequestBody UpdateFieldRequestDTO fieldDTO) {
        return fieldService.updateFieldById(fieldId, fieldDTO);
    }
}
