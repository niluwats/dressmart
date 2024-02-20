package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequestDto;
import com.nw.dressmart.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class CustomInventoryMapper implements InventoryMapper{
    @Override
    public Inventory inventoryDtoToInventoryItem(InventoryRequestDto inventoryRequestDto) {
        Inventory inventoryItem=new Inventory();
        inventoryItem.setQuantity(inventoryRequestDto.getQuantity());
        inventoryItem.setCreatedOn(LocalDateTime.now());
        return inventoryItem;
    }

    @Override
    public InventoryDto inventoryItemToInventoryDto(Inventory inventoryItem) {
        InventoryDto inventoryDto=new InventoryDto();
        inventoryDto.setStockId(inventoryItem.getId());
        inventoryDto.setItemId(inventoryItem.getItem().getId());
        inventoryDto.setItemName(inventoryItem.getItem().getName());
        inventoryDto.setQuantity(inventoryItem.getQuantity());
        inventoryDto.setCreatedOn(inventoryItem.getCreatedOn());

        return inventoryDto;
    }
}
