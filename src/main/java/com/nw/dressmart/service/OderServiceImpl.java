package com.nw.dressmart.service;

import com.nw.dressmart.dto.OrderRequestDto;
import com.nw.dressmart.dto.OrderResponse;
import com.nw.dressmart.entity.Order;
import com.nw.dressmart.mappers.CustomOrderMapper;
import com.nw.dressmart.repository.OrderProductRepository;
import com.nw.dressmart.repository.OrderRepository;
import com.nw.dressmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service()
public class OderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomOrderMapper customOrderMapper;

    @Override
    public OrderResponse newOrder(OrderRequestDto orderRequestDto, Long userId) {
        Long orderId;

        userRepository.findById(userId).orElseThrow(()->
                new IllegalStateException("user not found"));

        try {
            orderId= orderRepository.insertOrder(userId);

            orderRequestDto.getOrderItems().forEach((product, quantity) -> {
                orderProductRepository.insertOrderProduct(orderId,Long.parseLong(product),quantity);
            });
        }catch (Exception e){
            throw new IllegalStateException("Failed to create order "+e.getMessage());
        }

       Order order= orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalStateException("order not found"));
        return customOrderMapper.convertToOrderResponse(order);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        Order order=orderRepository.findById(id).orElseThrow(()->
                new IllegalStateException("order not found"));

        return customOrderMapper.convertToOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getOrders() {
        List<Order> orders=orderRepository.findAll();
        return orders.stream().map(customOrderMapper::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrders(Long userId) {
        List<Order> orders=orderRepository.findAllByUser_Id(userId);
        return orders.stream().map(customOrderMapper::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder() {

    }
}
