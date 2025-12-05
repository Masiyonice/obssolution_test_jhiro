package com.obs.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestOrderDTO {
    private String id;
    private Integer itemId;
    private Integer quantity;
}
