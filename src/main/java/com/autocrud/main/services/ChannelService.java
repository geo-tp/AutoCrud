package com.autocrud.main.services;

import com.autocrud.main.dtos.ChannelDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.User;
import com.autocrud.main.exceptions.custom.ChannelNotFoundException;
import com.autocrud.main.exceptions.custom.UserNotFoundException;
import com.autocrud.main.repositories.ChannelRepository;
import com.autocrud.main.repositories.UserRepository;
import com.autocrud.main.transformers.ChannelTransformer;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final FieldService fieldService;
    private final ChannelTransformer channelTransformer;

    public ChannelService(ChannelRepository channelRepository, UserRepository userRepository, FieldService fieldService, ChannelTransformer channelTransformer) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.fieldService = fieldService;
        this.channelTransformer = channelTransformer;
    }

    // Create a new channel
    public ChannelDTO createChannelFromDTO(ChannelDTO channelDTO) {
        User owner = userRepository.findById(channelDTO.getOwnerId())
            .orElseThrow(() -> new UserNotFoundException(channelDTO.getOwnerId()));

        Channel channel = channelTransformer.convertToEntity(channelDTO, owner);
        channel.setFields(fieldService.createFieldsFromDTO(channelDTO.getFields(), channel));

        return channelTransformer.convertToDTO(channelRepository.save(channel));
    }

    // Get a channel by ID
    public ChannelDTO getChannelById(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        return channelTransformer.convertToDTO(channel);
    }

    // Delete a channel by ID
    public void deleteChannel(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        channelRepository.delete(channel);
    }

    // Update an existing channel
    public ChannelDTO updateChannel(Long channelId, ChannelDTO channelDTO) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));

        User owner = userRepository.findById(channelDTO.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException(channelDTO.getOwnerId()));

        channel.setChannelName(channelDTO.getChannelName());
        channel.setOwner(owner);
        channel.setFields(fieldService.createFieldsFromDTO(channelDTO.getFields(), channel));

        Channel updatedChannel = channelRepository.save(channel);
        return channelTransformer.convertToDTO(updatedChannel);
    }

    // Get the ownerId by channelId
    public Long getOwnerIdByChannelId(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        return channel.getOwner().getId();
    }
}
