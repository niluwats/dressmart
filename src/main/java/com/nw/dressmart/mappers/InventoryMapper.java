package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequestDto;
import com.nw.dressmart.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

public interface InventoryMapper {
    Inventory inventoryDtoToInventoryItem(InventoryRequestDto inventoryRequestDto);

   InventoryDto inventoryItemToInventoryDto(Inventory inventoryItem);

}
