package com.obs.test.mapper;

import com.obs.test.dto.ItemDTO;
import com.obs.test.entity.Item;


public class ItemMapper {
    public static Item toEntity(ItemDTO request){
        return Item.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }
    public static ItemDTO toDTO(Item item){
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }
}
