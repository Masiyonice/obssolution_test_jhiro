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
public class RequestInventoryCreate {
    private Integer id;
    private Integer itemId;
    private Integer qty;
    private TypeTransaction typeTransaction;
}
