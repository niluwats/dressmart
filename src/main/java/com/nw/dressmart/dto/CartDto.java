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
    private BigDecimal totalPrice;
    private LocalDateTime updatedTimestamp;
    private List<CartItemDto> cartItems;
}
