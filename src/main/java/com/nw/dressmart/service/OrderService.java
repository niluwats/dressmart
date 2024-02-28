package com.nw.dressmart.service;

import com.nw.dressmart.dto.OrderRequestDto;
import com.nw.dressmart.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse newOrder(OrderRequestDto orderRequestDto, Long userId);
    OrderResponse getOrder(Long id);
    List<OrderResponse>getOrders();
    List<OrderResponse> getOrders(Long userId);
    void cancelOrder();
}
