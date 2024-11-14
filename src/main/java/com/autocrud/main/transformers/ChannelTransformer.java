package com.autocrud.main.transformers;

import com.autocrud.main.dtos.ChannelResponseDTO;
import com.autocrud.main.entities.Channel;
import com.autocrud.main.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ChannelTransformer {

    private final FieldTransformer fieldTransformer;

    public ChannelTransformer(FieldTransformer fieldTransformer) {
        this.fieldTransformer = fieldTransformer;
    }

    // Convert Channel entity to ChannelDTO
    public ChannelResponseDTO convertToDTO(Channel channel) {
        ChannelResponseDTO dto = new ChannelResponseDTO();
        dto.setId(channel.getId());
        dto.setChannelName(channel.getChannelName());
        dto.setOwnerId(channel.getOwner().getId());
        dto.setFields(fieldTransformer.convertToDTOs(channel.getFields()));
        return dto;
    }

    // Convert ChannelDTO to Channel entity (without setting fields)
    public Channel convertToEntity(ChannelResponseDTO channelDTO, User owner) {
        Channel channel = new Channel();
        channel.setChannelName(channelDTO.getChannelName());
        channel.setOwner(owner);
        return channel;
    }
}
