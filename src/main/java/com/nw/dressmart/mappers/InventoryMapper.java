package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequestDto;
import com.nw.dressmart.entity.InventoryItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InventoryMapper {
    default InventoryItem inventoryDtoToInventoryItem(InventoryRequestDto inventoryRequestDto){
        InventoryItem inventoryItem=new InventoryItem();
        inventoryItem.setQuantity(inventoryRequestDto.getQuantity());
        inventoryItem.setCreatedOn(LocalDateTime.now());
        return inventoryItem;
    }

    default InventoryDto inventoryItemToInventoryDto(InventoryItem inventoryItem){
        InventoryDto inventoryDto=new InventoryDto();
        inventoryDto.setStockId(inventoryItem.getId());
        inventoryDto.setItemId(inventoryItem.getItem().getId());
        inventoryDto.setItemName(inventoryItem.getItem().getName());
        inventoryDto.setQuantity(inventoryItem.getQuantity());
        inventoryDto.setCreatedOn(inventoryItem.getCreatedOn());

        return inventoryDto;
    }
}
