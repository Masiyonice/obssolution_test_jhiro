package com.obs.test.service;

import com.obs.test.dto.ItemDTO;
import com.obs.test.entity.Item;
import com.obs.test.mapper.ItemMapper;
import com.obs.test.repository.ItemRepository;
import com.obs.test.service.implementation.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void testCreateData_PositiveCase() {
        ItemDTO request = new ItemDTO(1, "Item1", 100);
        Item entity = ItemMapper.toEntity(request);
        Item savedEntity = Item.builder()
                .id(1)
                .name("Item1")
                .price(100)
                .build();

        when(itemRepository.save(entity)).thenReturn(savedEntity);
        ItemDTO result = itemService.createData(request);
        assertNotNull(result);
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getPrice(), result.getPrice());
        verify(itemRepository, times(1)).save(entity);
    }

    @Test
    public void testCreateData_NegativeCase() {
        ItemDTO request = new ItemDTO(1, "Item1", 100);
        Item entity = ItemMapper.toEntity(request);
        when(itemRepository.save(entity)).thenThrow(new RuntimeException("Database error"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.createData(request));
        assertEquals("Database error", exception.getMessage());
        verify(itemRepository, times(1)).save(entity);
    }
}
