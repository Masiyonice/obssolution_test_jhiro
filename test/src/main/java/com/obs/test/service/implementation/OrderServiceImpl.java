package com.obs.test.service.implementation;

import com.obs.test.repository.OrderRepository;
import com.obs.test.repository.TriggerRepo;
import com.obs.test.service.ItemService;
import com.obs.test.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final TriggerRepo triggerRepo;
    private final ItemService itemService;
}
