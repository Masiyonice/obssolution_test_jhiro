package com.obs.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DataOrderWithTotalDTO {
    List<ResponseOrderDTO> data;
    Integer totalPages;
}
