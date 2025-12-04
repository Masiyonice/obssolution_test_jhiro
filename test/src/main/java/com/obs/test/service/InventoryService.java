package com.obs.test.service;

import com.obs.test.dto.DataInventoryWithTotalData;
import com.obs.test.dto.RequestInventoryCreate;
import com.obs.test.dto.ResponseInventoryDTO;

import java.util.List;

public interface InventoryService {
    String createDataInventory(RequestInventoryCreate request);
    ResponseInventoryDTO getDataById(Integer id);
    DataInventoryWithTotalData getAllDataInventory(Integer page, Integer size);
}
