package com.autocrud.main.controllers;

import com.autocrud.main.dtos.ChannelDTO;
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
    public ChannelDTO createChannel(@RequestBody ChannelDTO channelDTO) {
        return channelService.createChannelFromDTO(channelDTO);
    }

    // Get a Channel by ID
    @CheckOwnership(resourceId = "id", resourceType = "channel")
    @GetMapping("/{id}")
    public ChannelDTO getChannelById(@PathVariable Long id) {
        return channelService.getChannelById(id);
    }

    // Update a Channel by ID
    @CheckOwnership(resourceId = "id", resourceType = "channel")
    @PutMapping("/{id}")
    public ChannelDTO updateChannel(@PathVariable Long id, @RequestBody ChannelDTO channelDTO) {
        return channelService.updateChannel(id, channelDTO);
    }

    // Delete a Channel by ID
    @CheckOwnership(resourceId = "id", resourceType = "channel")
    @DeleteMapping("/{id}")
    public ChannelDTO deleteChannel(@PathVariable Long id) {
        return channelService.deleteChannel(id);
    }
}
