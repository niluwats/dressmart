package com.nw.dressmart.repository;

import com.nw.dressmart.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
    @Procedure(name = "create_order_product")
    void insertOrderProduct(
            @Param("param_order_id") Long orderId,
            @Param("param_product_id") Long productId,
            @Param("param_quantity") int quantity
    );
}
