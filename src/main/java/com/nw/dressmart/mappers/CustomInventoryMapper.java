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
    public Inventory inventoryDtoToInventoryProduct(InventoryRequestDto inventoryRequestDto) {
        Inventory inventoryProduct=new Inventory();
        inventoryProduct.setQuantity(inventoryRequestDto.getQuantity());
        inventoryProduct.setCreatedOn(LocalDateTime.now());
        return inventoryProduct;
    }

    @Override
    public InventoryDto inventoryProductToInventoryDto(Inventory inventoryProduct) {
        InventoryDto inventoryDto=new InventoryDto();
        inventoryDto.setStockId(inventoryProduct.getId());
        inventoryDto.setProductId(inventoryProduct.getProduct().getId());
        inventoryDto.setProductName(inventoryProduct.getProduct().getName());
        inventoryDto.setQuantity(inventoryProduct.getQuantity());
        inventoryDto.setCreatedOn(inventoryProduct.getCreatedOn());

        return inventoryDto;
    }
}
