package com.obs.test.service.implementation;

import com.obs.test.dto.DataItemWithTotalDTO;
import com.obs.test.dto.ItemDTO;
import com.obs.test.entity.Item;
import com.obs.test.mapper.ItemMapper;
import com.obs.test.repository.ItemRepository;
import com.obs.test.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    @Override
    public DataItemWithTotalDTO getMainPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Item> all = itemRepository.findAll(pageable);
        List<ItemDTO> list = all.map(ItemMapper::toDTO).toList();
        int totalPages = all.getTotalPages();
        DataItemWithTotalDTO result = DataItemWithTotalDTO.builder()
                .items(list)
                .totalPages(totalPages)
                .build();
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ItemDTO updateData(ItemDTO request) {
        Item itemNotFound = itemRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Item not found"));
        itemNotFound.setName(request.getName());
        itemNotFound.setPrice(request.getPrice());
        itemRepository.save(itemNotFound);
        return request;
    }

    @Override
    public void deleteData(Integer id) {
        Item itemNotFound = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        itemRepository.delete(itemNotFound);
    }

    @Override
    public Item getItemForService(Integer id) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }
}
