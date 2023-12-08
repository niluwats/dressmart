package com.nw.dressmart.service;

import com.nw.dressmart.dto.AddToCartRequestDto;
import com.nw.dressmart.dto.CartDto;
import com.nw.dressmart.dto.CartItemDto;

public interface CartItemService {
    CartItemDto addToCart(Long cartId, AddToCartRequestDto request);

    CartDto getCart(Long id);

    CartItemDto getCartItem(Long cartItemId);
    void removeFromCart(Long cartId,Long cartItemId);
}
