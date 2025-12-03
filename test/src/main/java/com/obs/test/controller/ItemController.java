package com.obs.test.controller;

import com.obs.test.dto.ItemSubmitDTO;
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
    public ResponseEntity<GenericResponseDTO<ItemSubmitDTO>> createItem(@RequestBody ItemSubmitDTO request) {
        ItemSubmitDTO createdItem = itemService.createData(request);
        GenericResponseDTO<ItemSubmitDTO> response = GenericResponseDTO.<ItemSubmitDTO>builder()
                .data(createdItem)
                .message("Item created successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }
}
