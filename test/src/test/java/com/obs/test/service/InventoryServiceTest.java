package com.obs.test.service;

import com.obs.test.dto.ItemDTO;
import com.obs.test.dto.RequestInventoryCreate;
import com.obs.test.entity.Inventory;
import com.obs.test.entity.Item;
import com.obs.test.mapper.ItemMapper;
import com.obs.test.repository.InventoryReporsitory;
import com.obs.test.service.implementation.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
    @Mock
    private InventoryReporsitory inventoryReporsitory;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    public void testCreateData_PositiveCase() {
        ItemDTO dummyItemDTO = ItemDTO.builder()
                .id(1)
                .name("Dummy Item")
                .price(50)
                .build();
        Item dummyItem = ItemMapper.toEntity(dummyItemDTO);  // Assuming ItemMapper.toEntity exists

        RequestInventoryCreate request = RequestInventoryCreate.builder()
                .id(1)
                .itemId(1)
                .qty(10)
                .build();

        when(itemService.getDataById(request.getItemId())).thenReturn(dummyItemDTO);

        Inventory expectedEntity = Inventory.builder()
                .id(1)
                .item(dummyItem)
                .qty(10)
                .build();

        when(inventoryReporsitory.save(any(Inventory.class))).thenReturn(expectedEntity);
        String result = inventoryService.createDataInventory(request);
        assertEquals("success", result);
        verify(itemService, times(1)).getDataById(request.getItemId());
        verify(inventoryReporsitory, times(1)).save(any(Inventory.class));
    }

    @Test
    public void testCreateData_NegativeCase() {
        RequestInventoryCreate request = RequestInventoryCreate.builder()
                .id(1)
                .itemId(1)
                .qty(10)
                .build();
        when(itemService.getDataById(request.getItemId())).thenThrow(new RuntimeException("Item not found"));
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> inventoryService.createDataInventory(request));
        assertEquals("Item not found", exception.getMessage());
        verify(itemService, times(1)).getDataById(request.getItemId());
    }
}
