package com.nw.dressmart.service;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequestDto;

import java.util.List;

public interface InventoryService {
    InventoryDto addNewStock(InventoryRequestDto inventoryUpdateRequest);
    InventoryDto getStock(Long id);
    List<InventoryDto> getStocks();
    List<InventoryDto> getItemStocks(Long itemId);
    InventoryDto updateInventory(Long id, InventoryRequestDto inventoryUpdateRequest);
    String deleteStock(Long id);
}
