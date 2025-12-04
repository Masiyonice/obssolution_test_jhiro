package com.obs.test.service.implementation;

import com.obs.test.dto.ItemDTO;
import com.obs.test.dto.RequestInventoryCreate;
import com.obs.test.entity.Inventory;
import com.obs.test.entity.Item;
import com.obs.test.mapper.InventoryMapper;
import com.obs.test.mapper.ItemMapper;
import com.obs.test.repository.InventoryReporsitory;
import com.obs.test.service.InventoryService;
import com.obs.test.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryReporsitory inventoryReporsitory;
    private final ItemService itemService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createDataInventory(RequestInventoryCreate request) {
        ItemDTO dataById = itemService.getDataById(request.getId());
        Item entity = ItemMapper.toEntity(dataById);
        Inventory entity1 = InventoryMapper.toEntity(request, entity);
        inventoryReporsitory.save(entity1);
        return "success";
    }
}
