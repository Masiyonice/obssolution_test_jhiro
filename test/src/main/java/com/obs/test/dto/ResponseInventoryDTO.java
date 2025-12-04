package com.obs.test.dto;

import com.obs.test.utils.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseInventoryDTO {
    private Integer id;
    private ItemDTO item;
    private Integer qty;
    private TypeTransaction typeTransaction;
}
