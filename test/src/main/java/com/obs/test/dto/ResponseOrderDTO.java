package com.obs.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseOrderDTO {
    private String orderId;
    private ItemDTO item;
    private Integer quantity;
    private Integer price;
}
