package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.OrderProductResponse;
import com.nw.dressmart.dto.OrderResponse;
import com.nw.dressmart.entity.Order;
import com.nw.dressmart.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class CustomOrderMapper implements OrderMapper{

    @Override
    public OrderResponse convertToOrderResponse(Order order) {
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderId(order.getId());
        orderResponse.setTotal(order.getTotalPrice());
        orderResponse.setCreatedOn(order.getOrderTimestamp());

        orderResponse.setOrderProducts(order.getOrderProducts()
                .stream()
                .map(this::convertToOrderProductResponse)
                .collect(Collectors.toList()));
        
        return  orderResponse;
    }

    @Override
    public OrderProductResponse convertToOrderProductResponse(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getId(),
                orderProduct.getProduct().getName(),
                orderProduct.getQuantity(),
                orderProduct.getSubTotal()
        );
    }
}
