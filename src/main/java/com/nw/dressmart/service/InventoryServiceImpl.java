package com.nw.dressmart.service;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequest;
import com.nw.dressmart.entity.InventoryItem;
import com.nw.dressmart.entity.Item;
import com.nw.dressmart.repository.InventoryRepository;
import com.nw.dressmart.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private  final InventoryRepository inventoryRepository;
    private  final ItemRepository itemRepository;

    @Override
    public InventoryDto addNewStock(InventoryRequest inventoryRequest) {
        Item item=itemRepository.findById(inventoryRequest.getItemId()).orElseThrow(()->
                new IllegalStateException("item id "+inventoryRequest.getItemId()+" not found"));

        InventoryItem inventoryItem=convertToEntity(inventoryRequest,item);

        inventoryRepository.save(inventoryItem);
        return convertToDto(inventoryItem);
    }

    @Override
    public InventoryDto getStock(Long id) {
        InventoryItem inventoryItem=inventoryRepository.findById(id).orElseThrow(()->
                new IllegalStateException("inventory id "+id+" not found"));

        return convertToDto(inventoryItem);
    }

    @Override
    public List<InventoryDto> getStocks() {
        List<InventoryItem> inventoryItems=inventoryRepository.findAll();
        return inventoryItems.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<InventoryDto> getItemStocks(Long itemId) {
        List<InventoryItem> inventoryItems=inventoryRepository.findByItem_Id(itemId);
        return inventoryItems.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public InventoryDto updateInventory(Long id,InventoryRequest inventoryRequest) {
        Long itemId=inventoryRequest.getItemId();

        InventoryItem inventoryItem=inventoryRepository.findById(id).
                orElseThrow(()->new IllegalStateException("stock id "+id+" not found"));

        inventoryItem.setQuantity(inventoryRequest.getQuantity());

        if(!Objects.equals(itemId,inventoryItem.getItem().getId())){
            Item item=itemRepository.findById(itemId).orElseThrow(()->
                    new IllegalStateException("item id "+itemId+" not found"));

            inventoryItem.setItem(item);
        }

        return convertToDto(inventoryItem);
    }

    @Override
    public String deleteStock(Long id) {
        inventoryRepository.findById(id).
                orElseThrow(()->new IllegalStateException("stock id "+id+" not found"));

        System.out.println("stock id "+id);
        inventoryRepository.deleteById(id);
        return "deleted";
    }

    private InventoryItem convertToEntity(InventoryRequest request,Item item){
        InventoryItem inventoryItem=new InventoryItem();
        inventoryItem.setItem(item);
        inventoryItem.setQuantity(request.getQuantity());
        inventoryItem.setCreatedOn(LocalDateTime.now());

        return inventoryItem;
    }
    private InventoryDto convertToDto(InventoryItem inventoryItem){
        InventoryDto inventoryDto=new InventoryDto();
        inventoryDto.setStockId(inventoryItem.getId());
        inventoryDto.setItemId(inventoryItem.getItem().getId());
        inventoryDto.setItemName(inventoryItem.getItem().getName());
        inventoryDto.setQuantity(inventoryItem.getQuantity());
        inventoryDto.setCreatedOn(inventoryItem.getCreatedOn());

        return inventoryDto;
    }


}
