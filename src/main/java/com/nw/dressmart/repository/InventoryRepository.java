package com.nw.dressmart.repository;

import com.nw.dressmart.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem,Long> {
    List<InventoryItem> findByItem_Id(Long itemId);
}
