package com.nw.dressmart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCart {
    private Long itemId;
    private Integer quantity;
}
