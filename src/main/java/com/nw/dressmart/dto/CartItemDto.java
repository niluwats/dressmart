package com.nw.dressmart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CartItemDto {
    private Long id;
    private String name;
    private Long inventoryItemId;
    private Integer quantity;
    private BigDecimal subTotal;
    private LocalDateTime addedTimestamp;
    private LocalDateTime updatedTimestamp;
}
