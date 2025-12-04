package com.obs.test.service.implementation;

import com.obs.test.dto.RequestInventoryCreate;
import com.obs.test.dto.RequestOrderDTO;
import com.obs.test.entity.Inventory;
import com.obs.test.entity.Item;
import com.obs.test.entity.Order;
import com.obs.test.mapper.InventoryMapper;
import com.obs.test.mapper.OrderMapper;
import com.obs.test.repository.OrderRepository;
import com.obs.test.repository.TriggerRepo;
import com.obs.test.service.InventoryService;
import com.obs.test.service.ItemService;
import com.obs.test.service.OrderService;
import com.obs.test.utils.CustomException;
import com.obs.test.utils.TypeTransaction;
import lombok.RequiredArgsConstructor;
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
        RequestInventoryCreate fromOrder = InventoryMapper.createFromOrder(order);
        inventoryService.createDataInventory(fromOrder);
        return "Success create order with order no "+idOrder;
    }
}
