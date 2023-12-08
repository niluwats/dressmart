package com.nw.dressmart.repository;

import com.nw.dressmart.entity.CartItem;
import com.nw.dressmart.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{

    CartItem findByInventoryItemIdAndCartId(Long inventoryItemId,Long cartId);
}
