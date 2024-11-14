package com.autocrud.main.controllers;

import com.autocrud.main.dtos.ChannelResponseDTO;
import com.autocrud.main.services.ChannelService;
import org.springframework.web.bind.annotation.*;
import com.autocrud.main.annotations.*;;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    // Create a new Channel
    @PostMapping("/create")
    public ChannelResponseDTO createChannel(@RequestBody ChannelResponseDTO channelDTO) {
        return channelService.createChannelFromDTO(channelDTO);
    }

    // Get a Channel by ID
    @CheckOwnership(resourceId = "id", resourceType = "channel")
    @GetMapping("/{id}")
    public ChannelResponseDTO getChannelById(@PathVariable Long id) {
        return channelService.getChannelById(id);
    }

    // Update a Channel by ID
    @CheckOwnership(resourceId = "id", resourceType = "channel")
    @PutMapping("/{id}")
    public ChannelResponseDTO updateChannel(@PathVariable Long id, @RequestBody ChannelResponseDTO channelDTO) {
        return channelService.updateChannel(id, channelDTO);
    }

    // Delete a Channel by ID
    @CheckOwnership(resourceId = "id", resourceType = "channel")
    @DeleteMapping("/{id}")
    public ChannelResponseDTO deleteChannel(@PathVariable Long id) {
        return channelService.deleteChannel(id);
    }
}
