package com.nw.dressmart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderProductResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal subtotal;
}
