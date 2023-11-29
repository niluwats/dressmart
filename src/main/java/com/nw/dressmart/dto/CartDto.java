package com.nw.dressmart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private Long id;
    private Long userId;
    private BigDecimal TotalPrice;
    private LocalDateTime updatedOn;
    private List<CartItemDto> cartItems;
}
