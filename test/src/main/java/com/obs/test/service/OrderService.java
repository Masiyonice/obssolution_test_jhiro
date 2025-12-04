package com.obs.test.service;

import com.obs.test.dto.RequestOrderDTO;

public interface OrderService {
    String createOrder(RequestOrderDTO request) throws Exception;
}
