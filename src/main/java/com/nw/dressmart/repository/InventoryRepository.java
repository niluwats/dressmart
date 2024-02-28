package com.nw.dressmart.repository;

import com.nw.dressmart.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    List<Inventory> findAllByProduct_Id(Long productId);
}
