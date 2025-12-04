package com.obs.test.controller;

import com.obs.test.dto.RequestOrderDTO;
import com.obs.test.service.OrderService;
import com.obs.test.utils.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<GenericResponseDTO<String>> createOrder(@RequestBody RequestOrderDTO request) throws Exception {
        String order = orderService.createOrder(request);
        GenericResponseDTO<String> response = GenericResponseDTO.<String>builder()
                .data(order)
                .message("Order created successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }
}
