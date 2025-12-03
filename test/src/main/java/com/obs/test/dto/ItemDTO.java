package com.obs.test.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemSubmitDTO {
    private Integer id;
    private String name;
    private Integer price;
}
