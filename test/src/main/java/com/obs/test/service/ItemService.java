package com.obs.test.service;

import com.obs.test.dto.ItemSubmitDTO;
import org.springframework.stereotype.Service;


public interface ItemService {
    ItemSubmitDTO createData(ItemSubmitDTO request);
}
