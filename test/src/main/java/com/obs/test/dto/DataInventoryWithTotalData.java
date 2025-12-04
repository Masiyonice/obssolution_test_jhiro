package com.obs.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class DataInventoryWithTotalData {
    private List<ResponseInventoryDTO> data;
    private Integer totalData;
}
