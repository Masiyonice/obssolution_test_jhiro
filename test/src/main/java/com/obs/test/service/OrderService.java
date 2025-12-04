package com.obs.test.service;

import com.obs.test.dto.DataOrderWithTotalDTO;
import com.obs.test.dto.RequestOrderDTO;
import com.obs.test.dto.ResponseOrderDTO;

public interface OrderService {
    String createOrder(RequestOrderDTO request) throws Exception;
    ResponseOrderDTO getOrderById(String orderId);
    DataOrderWithTotalDTO getMainPage(Integer page, Integer size);
    String updateOrder(RequestOrderDTO request) throws Exception;
}
