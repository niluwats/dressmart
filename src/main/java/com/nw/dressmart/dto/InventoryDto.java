package com.nw.dressmart.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class InventoryDto {
    private Long stockId;
    private Long itemId;
    private String itemName;
    private Integer quantity;
    private LocalDateTime createdOn;
}
