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

    @Test
    public void updateOrder_IfItemChange_PositiveCase() throws Exception {
        RequestOrderDTO request = RequestOrderDTO.builder()
                .id("O1")
                .itemId(2)
                .quantity(5)
                .build();
        Inventory existingInventory =  Inventory.builder()
                .id(1)
                .item(null)
                .qty(10)
                .typeTransaction(TypeTransaction.T)
                .build();
        Item existingItem = Item.builder()
                .id(1)
                .name("Existing Item")
                .price(500)
                .inventories(List.of(existingInventory))
                .build();
        Order existingOrder = Order.builder()
                .orderNo("O1")
                .item(existingItem)
                .quantity(3)
                .price(500)
                .build();
        when(orderRepository.findById("O1")).thenReturn(java.util.Optional.of(existingOrder));
        Inventory newInventory =  Inventory.builder()
                .id(2)
                .item(null)
                .qty(10)
                .typeTransaction(TypeTransaction.T)
                .build();
        Item newItem = Item.builder()
                .id(2)
                .name("New Item")
                .price(600)
                .inventories(List.of(newInventory))
                .build();
        when(itemService.getItemForService(2)).thenReturn(newItem);
        when(inventoryService.createDataInventory(any())).thenReturn("success");
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);
        String result = orderService.updateOrder(request);
        assertEquals("Success update order with order no O1", result);
        verify(orderRepository, times(1)).findById("O1");
        verify(itemService, times(1)).getItemForService(2);
        verify(inventoryService, times(2)).createDataInventory(any());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void updateOrder_IfItemChange_NegativeCase_StockNotEnough() {
        RequestOrderDTO request = RequestOrderDTO.builder()
                .id("O1")
                .itemId(2)
                .quantity(15)
                .build();
        Inventory existingInventory =  Inventory.builder()
                .id(1)
                .item(null)
                .qty(10)
                .typeTransaction(TypeTransaction.T)
                .build();
        Item existingItem = Item.builder()
                .id(1)
                .name("Existing Item")
                .price(500)
                .inventories(List.of(existingInventory))
                .build();
        Order existingOrder = Order.builder()
                .orderNo("O1")
                .item(existingItem)
                .quantity(3)
                .price(500)
                .build();
        when(orderRepository.findById("O1")).thenReturn(java.util.Optional.of(existingOrder));
        Inventory newInventory =  Inventory.builder()
                .id(2)
                .item(null)
                .qty(10)
                .typeTransaction(TypeTransaction.T)
                .build();
        Item newItem = Item.builder()
                .id(2)
                .name("New Item")
                .price(600)
                .inventories(List.of(newInventory))
                .build();
        when(itemService.getItemForService(2)).thenReturn(newItem);
        try {
            orderService.updateOrder(request);
        } catch (Exception e) {
            assertEquals("Stock is not enough", e.getMessage());
        }
        verify(orderRepository, times(1)).findById("O1");
        verify(itemService, times(1)).getItemForService(2);
        verify(inventoryService, times(0)).createDataInventory(any());
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    public void updateOrder_IfItemChange_NegativeCase_InventoryEmpty() {
        RequestOrderDTO request = RequestOrderDTO.builder()
                .id("O1")
                .itemId(2)
                .quantity(5)
                .build();
        Inventory existingInventory =  Inventory.builder()
                .id(1)
                .item(null)
                .qty(10)
                .typeTransaction(TypeTransaction.T)
                .build();
        Item existingItem = Item.builder()
                .id(1)
                .name("Existing Item")
                .price(500)
                .inventories(List.of(existingInventory))
                .build();
        Order existingOrder = Order.builder()
                .orderNo("O1")
                .item(existingItem)
                .quantity(3)
                .price(500)
                .build();
        when(orderRepository.findById("O1")).thenReturn(java.util.Optional.of(existingOrder));
        Item newItem = Item.builder()
                .id(2)
                .name("New Item")
                .price(600)
                .inventories(List.of())
                .build();
        when(itemService.getItemForService(2)).thenReturn(newItem);
        try {
            orderService.updateOrder(request);
        } catch (Exception e) {
            assertEquals("Inventory is empty", e.getMessage());
        }
        verify(orderRepository, times(1)).findById("O1");
        verify(itemService, times(1)).getItemForService(2);
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    public void updateOrder_IfQuantityChange_PositiveCase() throws Exception {
        RequestOrderDTO request = RequestOrderDTO.builder()
                .id("O1")
                .itemId(1)
                .quantity(8)
                .build();
        Inventory existingInventory =  Inventory.builder()
                .id(1)
                .item(null)
                .qty(10)
                .typeTransaction(TypeTransaction.T)
                .build();
        Item existingItem = Item.builder()
                .id(1)
                .name("Existing Item")
                .price(500)
                .inventories(List.of(existingInventory))
                .build();
        Order existingOrder = Order.builder()
                .orderNo("O1")
                .item(existingItem)
                .quantity(5)
                .price(500)
                .build();
        when(orderRepository.findById("O1")).thenReturn(java.util.Optional.of(existingOrder));
//        doNothing().when(inventoryService).createDataInventory(any());
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);
        String result = orderService.updateOrder(request);
        assertEquals("Success update order with order no O1", result);
        verify(orderRepository, times(1)).findById("O1");
        verify(itemService, times(0)).getItemForService(anyInt());
        verify(inventoryService, times(1)).createDataInventory(any());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void updateOrder_IfQuantityChange_NegativeCase_StockNotEnough() {
        RequestOrderDTO request = RequestOrderDTO.builder()
                .id("O1")
                .itemId(1)
                .quantity(15)
                .build();
        Inventory existingInventory =  Inventory.builder()
                .id(1)
                .item(null)
                .qty(9)  // Changed to 9 to make stock insufficient (total=9, diff=10)
                .typeTransaction(TypeTransaction.T)
                .build();
        Item existingItem = Item.builder()
                .id(1)
                .name("Existing Item")
                .price(500)
                .inventories(List.of(existingInventory))
                .build();
        Order existingOrder = Order.builder()
                .orderNo("O1")
                .item(existingItem)
                .quantity(5)
                .price(500)
                .build();
        when(orderRepository.findById("O1")).thenReturn(java.util.Optional.of(existingOrder));
        try {
            orderService.updateOrder(request);
        } catch (Exception e) {
            assertEquals("Stock is not enough", e.getMessage());
        }
        verify(orderRepository, times(1)).findById("O1");
        verify(itemService, times(0)).getItemForService(anyInt());
        verify(inventoryService, times(0)).createDataInventory(any());
        verify(orderRepository, times(0)).save(any(Order.class));
    }

}
