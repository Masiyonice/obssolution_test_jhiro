package com.obs.test.service.implementation;

import com.obs.test.dto.*;
import com.obs.test.entity.Inventory;
import com.obs.test.entity.Item;
import com.obs.test.entity.Order;
import com.obs.test.mapper.InventoryMapper;
import com.obs.test.mapper.ItemMapper;
import com.obs.test.mapper.OrderMapper;
import com.obs.test.repository.OrderRepository;
import com.obs.test.repository.TriggerRepo;
import com.obs.test.service.InventoryService;
import com.obs.test.service.ItemService;
import com.obs.test.service.OrderService;
import com.obs.test.utils.CustomException;
import com.obs.test.utils.TypeTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TriggerRepo triggerRepo;
    private final ItemService itemService;
    private final InventoryService inventoryService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createOrder(RequestOrderDTO request) throws Exception {
        Item item = itemService.getItemForService(request.getItemId());
        List<Inventory> inventories = item.getInventories();
        if(inventories == null || inventories.isEmpty()){
            throw new CustomException("Inventory is empty");
        }
        Integer total = 0;
        for (Inventory inventory : inventories) {
            if(inventory.getTypeTransaction().equals(TypeTransaction.T)){
                total += inventory.getQty();
            }else{
                total -= inventory.getQty();
            }
        }
        if(total < request.getQuantity()){
            throw new CustomException("Stock is not enough");
        }
        Long nextSequenceValue = triggerRepo.getNextSequenceValue();
        String idOrder = "O"+nextSequenceValue;
        request.setId(idOrder);
        Order order = OrderMapper.mapperToEntity(request, item);
        orderRepository.save(order);
        RequestInventoryCreate fromOrder = InventoryMapper.createFromOrder(order, TypeTransaction.W);
        inventoryService.createDataInventory(fromOrder);
        return "Success create order with order no "+idOrder;
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseOrderDTO getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow( () -> new RuntimeException("Order not found"));
        ItemDTO item = ItemMapper.toDTO(order.getItem());
        return OrderMapper.mapperToDTO(order, item);
    }

    @Transactional(readOnly = true)
    @Override
    public DataOrderWithTotalDTO getMainPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> all = orderRepository.findAll(pageable);
        return DataOrderWithTotalDTO.builder()
                .data(all.map(order -> {
                    ItemDTO itemDTO = ItemMapper.toDTO(order.getItem());
                    return OrderMapper.mapperToDTO(order, itemDTO);
                }).toList())
                .totalPages(all.getTotalPages())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateOrder(RequestOrderDTO request) throws Exception {
        Order order = orderRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Order not found"));
        if(!request.getItemId().equals(order.getItem().getId())){
            adjustmentIfItemChange(order, request);
        }else{
            adjustmentIfQuantityChange(order, request);
        }
        return "Sucess update order with order no "+request.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        RequestInventoryCreate addBackInventory = InventoryMapper.createFromOrder(order, TypeTransaction.T);
        inventoryService.createDataInventory(addBackInventory);
        orderRepository.delete(order);
    }

    private void adjustmentIfItemChange(Order existingOrder, RequestOrderDTO request) throws CustomException {
        //add back previous item qty
        RequestInventoryCreate previousItem = InventoryMapper.createFromOrder(existingOrder, TypeTransaction.T);
        inventoryService.createDataInventory(previousItem);
        //reduce new item qty
        Item newItem = itemService.getItemForService(request.getItemId());
        Order newOrder = OrderMapper.mapperToEntity(request, newItem);
        List<Inventory> newInventories = newItem.getInventories();
        if(newInventories == null || newInventories.isEmpty()){
            throw new CustomException("Inventory is empty");
        }
        Integer total = 0;
        for (Inventory inventory : newInventories) {
            if(inventory.getTypeTransaction().equals(TypeTransaction.T)){
                total += inventory.getQty();
            }else{
                total -= inventory.getQty();
            }
        }
        if(total < request.getQuantity()){
            throw new CustomException("Stock is not enough");
        }
        RequestInventoryCreate newItemRequest = InventoryMapper.createFromOrder(newOrder, TypeTransaction.W);
        inventoryService.createDataInventory(newItemRequest);
        orderRepository.save(newOrder);
    }

    private void adjustmentIfQuantityChange(Order existingOrder, RequestOrderDTO request) throws Exception {
        List<Inventory> inventories = existingOrder.getItem().getInventories();
        Integer total = 0;
        for (Inventory inventory : inventories) {
            if(inventory.getTypeTransaction().equals(TypeTransaction.T)){
                total += inventory.getQty();
            }else{
                total -= inventory.getQty();
            }
        }
        if(request.getQuantity() > existingOrder.getQuantity()){
            Integer diff = request.getQuantity() - existingOrder.getQuantity();
            if(total < diff){
                throw new CustomException("Stock is not enough");
            }
            RequestInventoryCreate reduceRequest = RequestInventoryCreate.builder()
                    .itemId(existingOrder.getItem().getId())
                    .qty(diff)
                    .typeTransaction(TypeTransaction.W)
                    .build();
            inventoryService.createDataInventory(reduceRequest);
        }else if(request.getQuantity() < existingOrder.getQuantity()){
            Integer diff = existingOrder.getQuantity() - request.getQuantity();
            RequestInventoryCreate addRequest = RequestInventoryCreate.builder()
                    .itemId(existingOrder.getItem().getId())
                    .qty(diff)
                    .typeTransaction(TypeTransaction.T)
                    .build();
            inventoryService.createDataInventory(addRequest);
        }

    }
}
