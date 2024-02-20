package com.nw.dressmart.service;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequestDto;
import com.nw.dressmart.entity.Inventory;
import com.nw.dressmart.entity.Item;
import com.nw.dressmart.mappers.CustomInventoryMapper;
import com.nw.dressmart.mappers.InventoryMapper;
import com.nw.dressmart.repository.InventoryRepository;
import com.nw.dressmart.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomInventoryMapper inventoryMapper;

    @Override
    public InventoryDto addNewStock(InventoryRequestDto inventoryRequest) {
        Item item=itemRepository.findById(inventoryRequest.getItemId()).orElseThrow(()->
                new IllegalStateException("item id "+inventoryRequest.getItemId()+" not found"));

        Inventory inventoryItem=inventoryMapper.inventoryDtoToInventoryItem(inventoryRequest);
        inventoryItem.setItem(item);

        inventoryRepository.save(inventoryItem);
        return inventoryMapper.inventoryItemToInventoryDto(inventoryItem);
    }

    @Override
    public InventoryDto getStock(Long id) {
        Inventory inventoryItem=inventoryRepository.findById(id).orElseThrow(()->
                new IllegalStateException("inventory id "+id+" not found"));

        return inventoryMapper.inventoryItemToInventoryDto(inventoryItem);
    }

    @Override
    public List<InventoryDto> getStocks() {
        List<Inventory> inventoryItems=inventoryRepository.findAll();
        return inventoryItems.stream().map(inventoryMapper::inventoryItemToInventoryDto).collect(Collectors.toList());
    }

    @Override
    public List<InventoryDto> getItemStocks(Long itemId) {
        List<Inventory> inventoryItems=inventoryRepository.findAllByItem_Id(itemId);
        return inventoryItems.stream().map(inventoryMapper::inventoryItemToInventoryDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public InventoryDto updateInventory(Long id, InventoryRequestDto inventoryRequest) {
        Long itemId=inventoryRequest.getItemId();

        Inventory inventoryItem=inventoryRepository.findById(id).
                orElseThrow(()->new IllegalStateException("stock id "+id+" not found"));

        inventoryItem.setQuantity(inventoryRequest.getQuantity());

        if(!Objects.equals(itemId,inventoryItem.getItem().getId())){
            Item item=itemRepository.findById(itemId).orElseThrow(()->
                    new IllegalStateException("item id "+itemId+" not found"));

            inventoryItem.setItem(item);
        }

        return inventoryMapper.inventoryItemToInventoryDto(inventoryItem);
    }

    @Override
    public String deleteStock(Long id) {
        inventoryRepository.findById(id).
                orElseThrow(()->new IllegalStateException("stock id "+id+" not found"));

        System.out.println("stock id "+id);
        inventoryRepository.deleteById(id);
        return "deleted";
    }
}
