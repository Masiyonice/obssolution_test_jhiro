package com.obs.test.controller;

import com.obs.test.dto.DataInventoryWithTotalData;
import com.obs.test.dto.RequestInventoryCreate;
import com.obs.test.dto.ResponseInventoryDTO;
import com.obs.test.service.InventoryService;
import com.obs.test.utils.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponseDTO<String>> createInventory(@RequestBody RequestInventoryCreate request) {
        String result = inventoryService.createDataInventory(request);
        GenericResponseDTO<String> response = GenericResponseDTO.<String>builder()
                .data(result)
                .message("Inventory created successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<GenericResponseDTO<ResponseInventoryDTO>> getInventoryById(@PathVariable Integer id) {
        ResponseInventoryDTO result = inventoryService.getDataById(id);
        GenericResponseDTO<ResponseInventoryDTO> response = GenericResponseDTO.<ResponseInventoryDTO>builder()
                .data(result)
                .message("Inventory retrieved successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<GenericResponseDTO<?>> getAllInventory(@RequestParam Integer page,
                                                                 @RequestParam Integer size) {
        DataInventoryWithTotalData allDataInventory = inventoryService.getAllDataInventory(page, size);
        List<ResponseInventoryDTO> data = allDataInventory.getData();
        Integer totalData = allDataInventory.getTotalData();
        GenericResponseDTO<?> response = GenericResponseDTO.builder()
                .data(data)
                .message(HttpStatus.ACCEPTED.getReasonPhrase())
                .statusCode(200)
                .totalPages(totalData)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponseDTO<String>> updateInventory(@RequestBody RequestInventoryCreate request) {
        String result = inventoryService.update(request);
        GenericResponseDTO<String> response = GenericResponseDTO.<String>builder()
                .data(result)
                .message("Inventory updated successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponseDTO<String>> deleteInventory(@PathVariable Integer id) {
        inventoryService.delete(id);
        GenericResponseDTO<String> response = GenericResponseDTO.<String>builder()
                .data("Inventory deleted successfully")
                .message("Inventory deleted successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(response);
    }
}
