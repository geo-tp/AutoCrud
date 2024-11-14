package com.autocrud.main.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateChannelRequestDTO {
    private String channelName;
    private Long ownerId;
    private List<FieldResponseDTO> fields;
}
