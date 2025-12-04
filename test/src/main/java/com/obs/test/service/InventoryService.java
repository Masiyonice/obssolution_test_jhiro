package com.obs.test.service;

import com.obs.test.dto.RequestInventoryCreate;
import com.obs.test.dto.ResponseInventoryDTO;

public interface InventoryService {
    String createDataInventory(RequestInventoryCreate request);
    ResponseInventoryDTO getDataById(Integer id);
}
