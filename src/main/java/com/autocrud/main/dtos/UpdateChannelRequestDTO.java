package com.autocrud.main.dtos;

import java.util.List;

public class UpdateChannelRequestDTO {
    private String channelName;
    private List<FieldResponseDTO> fields;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<FieldResponseDTO> getFields() {
        return fields;
    }

    public void setFields(List<FieldResponseDTO> fields) {
        this.fields = fields;
    }
}
