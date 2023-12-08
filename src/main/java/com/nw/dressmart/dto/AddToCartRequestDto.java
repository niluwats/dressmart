package com.nw.dressmart.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddToCartRequestDto {
    private Long inventoryItemId;
    private Integer quantity;
}