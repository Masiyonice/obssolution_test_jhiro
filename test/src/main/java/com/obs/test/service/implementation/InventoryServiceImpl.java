package com.obs.test.service.implementation;

import com.obs.test.dto.DataInventoryWithTotalData;
import com.obs.test.dto.ItemDTO;
import com.obs.test.dto.RequestInventoryCreate;
import com.obs.test.dto.ResponseInventoryDTO;
import com.obs.test.entity.Inventory;
import com.obs.test.entity.Item;
import com.obs.test.mapper.InventoryMapper;
import com.obs.test.mapper.ItemMapper;
import com.obs.test.repository.InventoryReporsitory;
import com.obs.test.service.InventoryService;
import com.obs.test.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    @Override
    public ResponseInventoryDTO getDataById(Integer id) {
        Inventory dataFound = inventoryReporsitory.findById(id).orElseThrow(() -> new RuntimeException("Data not found"));
        return InventoryMapper.toDTO(dataFound);
    }

    @Transactional(readOnly = true)
    @Override
    public DataInventoryWithTotalData getAllDataInventory(Integer page, Integer size) {
        Pageable pageable = PageRequest.of( page - 1, size);
        Page<Inventory> all = inventoryReporsitory.findAll(pageable);
        int totalPages = all.getTotalPages();
        return DataInventoryWithTotalData.builder()
                .data(all.map(InventoryMapper::toDTO).toList())
                .totalData(totalPages)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(RequestInventoryCreate request) {
        boolean b = inventoryReporsitory.existsById(request.getId());
        if (!b){
            throw new RuntimeException("Data not found");
        }
        ItemDTO dataById = itemService.getDataById(request.getId());
        Item entity = ItemMapper.toEntity(dataById);
        Inventory entity1 = InventoryMapper.toEntity(request, entity);
        inventoryReporsitory.save(entity1);
        return "data updated successfully";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer id) {
        Inventory data = inventoryReporsitory.findById(id).orElseThrow(() -> new RuntimeException("Data not found"));
        inventoryReporsitory.delete(data);
    }


}
