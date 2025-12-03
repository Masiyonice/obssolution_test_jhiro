package com.obs.test.controller;

import com.obs.test.dto.ItemDTO;
import com.obs.test.service.ItemService;
import com.obs.test.utils.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponseDTO<ItemDTO>> createItem(@RequestBody ItemDTO request) {
        ItemDTO createdItem = itemService.createData(request);
        GenericResponseDTO<ItemDTO> response = GenericResponseDTO.<ItemDTO>builder()
                .data(createdItem)
                .message("Item created successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<GenericResponseDTO<ItemDTO>> getItemById(@PathVariable Integer id) {
        ItemDTO item = itemService.getDataById(id);
        GenericResponseDTO<ItemDTO> response = GenericResponseDTO.<ItemDTO>builder()
                .data(item)
                .message("Item retrieved successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }
}
