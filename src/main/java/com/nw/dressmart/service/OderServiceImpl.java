package com.nw.dressmart.service;

import com.nw.dressmart.dto.OrderRequestDto;
import com.nw.dressmart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public String newOrder(OrderRequestDto orderRequestDto,Long userId) {
        return null;
    }

    @Override
    public void getOrder() {

    }

    @Override
    public void getOrders() {

    }

    @Override
    public void cancelOrder() {

    }
}
