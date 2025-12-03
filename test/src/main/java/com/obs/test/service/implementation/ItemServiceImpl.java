package com.obs.test.service.implementation;

import com.obs.test.dto.ItemDTO;
import com.obs.test.entity.Item;
import com.obs.test.mapper.ItemMapper;
import com.obs.test.repository.ItemRepository;
import com.obs.test.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ItemDTO createData(ItemDTO request) {
        Item entity = ItemMapper.toEntity(request);
        Item savedItem = itemRepository.save(entity);
        return ItemMapper.toDTO(savedItem);
    }

    @Transactional(readOnly = true)
    @Override
    public ItemDTO getDataById(Integer id) {
        Item existing = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        return ItemMapper.toDTO(existing);
    }
}
