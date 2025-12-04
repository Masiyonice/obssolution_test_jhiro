package com.obs.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DataItemWithTotalDTO {
    private List<ItemDTO> items = new ArrayList<>();
    private Integer totalPages;
}
