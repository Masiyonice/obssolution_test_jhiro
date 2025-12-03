package com.obs.test.mapper;

import com.obs.test.dto.ItemSubmitDTO;
import com.obs.test.entity.Item;


public class ItemMapper {
    public static Item toEntity(ItemSubmitDTO request){
        return Item.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }
    public static ItemSubmitDTO toDTO(Item item){
        return ItemSubmitDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }
}
