package com.nw.dressmart.service;

import com.nw.dressmart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void newOrder() {

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
