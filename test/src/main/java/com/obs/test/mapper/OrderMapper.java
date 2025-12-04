package com.obs.test.mapper;

import com.obs.test.dto.RequestOrderDTO;
import com.obs.test.entity.Item;
import com.obs.test.entity.Order;

public class OrderMapper {
    public static Order mapperToEntity(RequestOrderDTO request, Item item){
        return Order.builder()
                .orderNo(request.getId())
                .item(item)
                .quantity(request.getQuantity())
                .price(item.getPrice())
                .build();
    }
}
