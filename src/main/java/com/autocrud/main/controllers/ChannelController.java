package com.autocrud.main.controllers;

import com.autocrud.main.models.ChannelDTO;
import com.autocrud.main.services.ChannelService;
import com.autocrud.main.services.EntryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService, EntryService entryService) {
        this.channelService = channelService;
    }

    @PostMapping("/create")
    public ChannelDTO createChannel(@RequestBody ChannelDTO channelDTO) {
        return channelService.convertToDTO(channelService.createChannelFromDTO(channelDTO));
    }
}
