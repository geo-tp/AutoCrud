package com.autocrud.main.services;

import com.autocrud.main.models.ChannelDTO;
import com.autocrud.main.exceptions.UserNotFoundException;
import com.autocrud.main.models.Channel;
import com.autocrud.main.models.User;
import com.autocrud.main.repositories.ChannelRepository;
import com.autocrud.main.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final FieldService fieldService;

    public ChannelService(ChannelRepository channelRepository, UserRepository userRepository, FieldService fieldService) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.fieldService = fieldService;
    }

    public Channel createChannelFromDTO(ChannelDTO channelDTO) {
        User owner = userRepository.findById(channelDTO.getOwnerId())
            .orElseThrow(() -> new UserNotFoundException(channelDTO.getOwnerId()));

        Channel channel = new Channel();
        channel.setChannelName(channelDTO.getChannelName());
        channel.setOwner(owner);

        channel.setFields(fieldService.createFieldsFromDTO(channelDTO.getFields(), channel));

        return channelRepository.save(channel);
    }

    public ChannelDTO convertToDTO(Channel channel) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(channel.getId());
        dto.setChannelName(channel.getChannelName());
        dto.setOwnerId(channel.getOwner().getId());

        dto.setFields(fieldService.convertFieldsToDTOs(channel.getFields()));

        return dto;
    }
}
