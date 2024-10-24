package com.autocrud.main.transformers;

import com.autocrud.main.dtos.ChannelDTO;
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
    public ChannelDTO convertToDTO(Channel channel) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(channel.getId());
        dto.setChannelName(channel.getChannelName());
        dto.setOwnerId(channel.getOwner().getId());
        dto.setFields(fieldTransformer.convertToDTOs(channel.getFields()));
        return dto;
    }

    // Convert ChannelDTO to Channel entity (without setting fields)
    public Channel convertToEntity(ChannelDTO channelDTO, User owner) {
        Channel channel = new Channel();
        channel.setChannelName(channelDTO.getChannelName());
        channel.setOwner(owner);
        return channel;
    }
}
