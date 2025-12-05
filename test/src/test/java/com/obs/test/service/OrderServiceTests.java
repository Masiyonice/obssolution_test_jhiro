package com.obs.test.service;

import com.obs.test.dto.RequestOrderDTO;
import com.obs.test.entity.Inventory;
import com.obs.test.entity.Item;
import com.obs.test.entity.Order;
import com.obs.test.repository.OrderRepository;
import com.obs.test.repository.TriggerRepo;
import com.obs.test.service.implementation.OrderServiceImpl;
import com.obs.test.utils.TypeTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TriggerRepo triggerRepo;
    @Mock
    private ItemService itemService;
    @Mock
    private InventoryService inventoryService;
    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    public void testCreteData_PositiveCase() throws Exception {
        RequestOrderDTO request = RequestOrderDTO.builder()
                .id("O1")
                .itemId(1)
                .quantity(5)
                .build();
        Inventory inventory =  Inventory.builder()
                .id(1)
                .item(null)
                .qty(10)
                .typeTransaction(TypeTransaction.T)
                .build();
        Item item = Item.builder()
                .id(1)
                .name("Dummy Item")
                .price(500)
                .inventories(List.of(inventory))
                .build();
        when(itemService.getItemForService(request.getItemId())).thenReturn(item);
        Order expectedResult = Order.builder()
                .orderNo("O1")
                .price(500)
                .item(item)
                .quantity(5)
                .build();
        when(triggerRepo.getNextSequenceValue()).thenReturn(1L);
        when(orderRepository.save(expectedResult)).thenReturn(expectedResult);
        String order = orderService.createOrder(request);
        assertEquals("Success create order with order no O1", order);
        verify(itemService, times(1)).getItemForService(request.getItemId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testCreateData_NegativeCase_InventoryEmpty() {
        RequestOrderDTO request = RequestOrderDTO.builder()
                .id("O1")
                .itemId(1)
                .quantity(5)
                .build();
        Item item = Item.builder()
                .id(1)
                .name("Dummy Item")
                .price(500)
                .inventories(List.of())
                .build();
        when(itemService.getItemForService(request.getItemId())).thenReturn(item);
        try {
            orderService.createOrder(request);
        } catch (Exception e) {
            assertEquals("Inventory is empty", e.getMessage());
        }
        verify(itemService, times(1)).getItemForService(request.getItemId());
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    public void testCreateData_NegativeCase_StockNotEnough() {
        RequestOrderDTO request = RequestOrderDTO.builder()
                .id("O1")
                .itemId(1)
                .quantity(15)
                .build();
        Inventory inventory =  Inventory.builder()
                .id(1)
                .item(null)
                .qty(10)
                .typeTransaction(TypeTransaction.T)
                .build();
        Item item = Item.builder()
                .id(1)
                .name("Dummy Item")
                .price(500)
                .inventories(List.of(inventory))
                .build();
        when(itemService.getItemForService(request.getItemId())).thenReturn(item);
        try {
            orderService.createOrder(request);
        } catch (Exception e) {
            assertEquals("Stock is not enough", e.getMessage());
        }
        verify(itemService, times(1)).getItemForService(request.getItemId());
        verify(orderRepository, times(0)).save(any(Order.class));
    }



}
