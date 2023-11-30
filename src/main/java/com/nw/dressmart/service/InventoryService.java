package com.nw.dressmart.service;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequest;

import java.util.List;

public interface InventoryService {
    InventoryDto addNewStock(InventoryRequest inventoryUpdateRequest);
    InventoryDto getStock(Long id);
    List<InventoryDto> getStocks();
    List<InventoryDto> getItemStocks(Long itemId);
    InventoryDto updateInventory(Long id,InventoryRequest inventoryUpdateRequest);
    String deleteStock(Long id);
}
