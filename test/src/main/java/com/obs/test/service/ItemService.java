package com.obs.test.service;

import com.obs.test.dto.DataItemWithTotalDTO;
import com.obs.test.dto.ItemDTO;
import com.obs.test.entity.Item;


public interface ItemService {
    ItemDTO createData(ItemDTO request);
    ItemDTO getDataById(Integer id);
    DataItemWithTotalDTO getMainPage(Integer page, Integer size);
    ItemDTO updateData(ItemDTO request);
    void deleteData(Integer id);
    Item getItemForService(Integer id);
}
