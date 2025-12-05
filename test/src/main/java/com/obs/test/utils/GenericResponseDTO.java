package com.obs.test.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponseDTO<T> {
    private String message;
    private T data;
    private Integer statusCode;
    private Integer totalPages;
}
