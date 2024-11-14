package com.autocrud.main.dtos;

import java.util.List;

public class ChannelResponseDTO {
    private Long id;
    private String channelName;
    private Long ownerId;
    private List<FieldResponseDTO> fields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<FieldResponseDTO> getFields() {
        return fields;
    }

    public void setFields(List<FieldResponseDTO> fields) {
        this.fields = fields;
    }
}
