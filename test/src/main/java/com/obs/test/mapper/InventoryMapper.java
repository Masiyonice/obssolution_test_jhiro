package com.obs.test.mapper;

import com.obs.test.dto.RequestInventoryCreate;
import com.obs.test.dto.ResponseInventoryDTO;
import com.obs.test.entity.Inventory;
import com.obs.test.entity.Item;
import com.obs.test.entity.Order;
import com.obs.test.utils.TypeTransaction;

public class InventoryMapper {

    public static Inventory toEntity(RequestInventoryCreate request, Item item){
        return Inventory.builder()
                .id(request.getId())
                .item(item)
                .qty(request.getQty())
                .typeTransaction(request.getTypeTransaction())
                .build();
    }

    public static ResponseInventoryDTO toDTO(Inventory inventory){
        return ResponseInventoryDTO.builder()
                .id(inventory.getId())
                .item(ItemMapper.toDTO(inventory.getItem()))
                .qty(inventory.getQty())
                .typeTransaction(inventory.getTypeTransaction().getDescription())
                .build();
    }

    public static RequestInventoryCreate createFromOrder(Order order, TypeTransaction typeTransaction){
        return RequestInventoryCreate.builder()
                .itemId(order.getItem().getId())
                .qty(order.getQuantity())
                .typeTransaction(typeTransaction)
                .build();
    }
}
