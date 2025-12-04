package com.obs.test.mapper;

import com.obs.test.dto.RequestInventoryCreate;
import com.obs.test.entity.Inventory;
import com.obs.test.entity.Item;

public class InventoryMapper {

    public static Inventory toEntity(RequestInventoryCreate request, Item item){
        return Inventory.builder()
                .id(request.getId())
                .item(item)
                .qty(request.getQty())
                .typeTransaction(request.getTypeTransaction())
                .build();
    }
}
