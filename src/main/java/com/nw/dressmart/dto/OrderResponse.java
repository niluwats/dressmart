package com.nw.dressmart.dto;

import com.nw.dressmart.entity.OrderProduct;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private BigDecimal total;
    private LocalDateTime createdOn;
    private List<OrderProductResponse> orderProducts;
}
