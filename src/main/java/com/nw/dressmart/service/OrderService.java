package com.nw.dressmart.service;

import com.nw.dressmart.dto.OrderRequestDto;

public interface OrderService {
    String newOrder(OrderRequestDto orderRequestDto,Long userId);
    void getOrder();
    void getOrders();
    void cancelOrder();
}
