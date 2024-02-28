package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.OrderProductResponse;
import com.nw.dressmart.dto.OrderRequestDto;
import com.nw.dressmart.dto.OrderResponse;
import com.nw.dressmart.entity.Order;
import com.nw.dressmart.entity.OrderProduct;

public interface OrderMapper {
    OrderResponse convertToOrderResponse(Order order);

    OrderProductResponse convertToOrderProductResponse(OrderProduct orderProduct);
}
