package com.obs.test.controller;

import com.obs.test.dto.DataOrderWithTotalDTO;
import com.obs.test.dto.RequestOrderDTO;
import com.obs.test.dto.ResponseOrderDTO;
import com.obs.test.service.OrderService;
import com.obs.test.utils.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("getById/{orderId}")
    public ResponseEntity<GenericResponseDTO<ResponseOrderDTO>> getOrderById(@PathVariable String orderId){
        ResponseOrderDTO order = orderService.getOrderById(orderId);
        GenericResponseDTO<ResponseOrderDTO> response = GenericResponseDTO.<ResponseOrderDTO>builder()
                .data(order)
                .message("Order retrieved successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<GenericResponseDTO<List<ResponseOrderDTO>>> getAllOrders(
            @RequestParam Integer page,
            @RequestParam Integer size
    ){
        DataOrderWithTotalDTO result = orderService.getMainPage(page, size);
        GenericResponseDTO<List<ResponseOrderDTO>> response = GenericResponseDTO.<List<ResponseOrderDTO>>builder()
                .data(result.getData())
                .totalPages(result.getTotalPages())
                .message("Orders retrieved successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponseDTO<String>> update(@RequestBody RequestOrderDTO request) throws Exception {
        String order = orderService.updateOrder(request);
        GenericResponseDTO<String> response = GenericResponseDTO.<String>builder()
                .data(order)
                .message("Order created successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<GenericResponseDTO<String>> deleteOrder(@PathVariable String orderId){
        orderService.deleteOrder(orderId);
        GenericResponseDTO<String> response = GenericResponseDTO.<String>builder()
                .data("Order deleted successfully")
                .message("Order deleted successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }
}
