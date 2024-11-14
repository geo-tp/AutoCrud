package com.autocrud.main.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFieldRequestDTO {
    private String fieldName;
    private String dataType;
    private Integer length;
}
