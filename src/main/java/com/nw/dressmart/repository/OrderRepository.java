package com.nw.dressmart.repository;

import com.nw.dressmart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Procedure(name = "create_order")
    Long insertOrder(@Param("param_user_id") Long userId);
    List<Order> findAllByUser_Id(Long userId);
}
