package com.nw.dressmart.repository;

import com.nw.dressmart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("select c from Cart c join CartItem ci on c.id=ci.cart.id where c.id=?1 and ci.status=TRUE")
    Optional<Cart> findByCartIdWithActiveItems(Long cartId);

    @Query("select count(ci)>0 from Cart c join CartItem ci on c.id=ci.cart.id where c.id=?1 and ci.id=?2 and ci.status=TRUE")
    Boolean cartContainsCartItem(Long cartId,Long cartItemId);
}
