package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.AddToCartRequestDto;
import com.nw.dressmart.dto.CartDto;
import com.nw.dressmart.dto.CartItemDto;
import com.nw.dressmart.entity.Cart;
import com.nw.dressmart.entity.CartItem;
import com.nw.dressmart.entity.InventoryItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {
    default CartItemDto cartItemToCartItemDto(CartItem cartItem){
        CartItemDto cartItemDto=new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setName(cartItem.getName());
        cartItemDto.setInventoryItemId(cartItem.getInventoryItem().getId());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setSubTotal(cartItem.getSubTotal());
        cartItemDto.setAddedTimestamp(cartItem.getAddedTimestamp());
        cartItemDto.setUpdatedTimestamp(cartItem.getUpdatedTimestamp());

        return cartItemDto;
    }

    default CartDto cartToCartDto(Cart cart){
        CartDto cartDto=new CartDto();

        List<CartItemDto> cartItems=cart.getCartItems().stream().map(this::cartItemToCartItemDto).toList();

        cartDto.setId(cart.getId());
        cartDto.setCartItems(cartItems);
        cartDto.setUserId(cart.getUser().getId());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setUpdatedTimestamp(cart.getUpdatedTimestamp());

        return cartDto;
    }

    default CartItem addToCartDtoToCartItem(AddToCartRequestDto addToCartRequestDto, InventoryItem inventoryItem, Cart cart){
        CartItem cartItem=new CartItem();

        cartItem.setQuantity(addToCartRequestDto.getQuantity());
        cartItem.setInventoryItem(inventoryItem);
        cartItem.setCart(cart);
        cartItem.setStatus(true);
        cartItem.setAddedTimestamp(LocalDateTime.now());
        cartItem.setUpdatedTimestamp(LocalDateTime.now());
        cartItem.setSubTotal(BigDecimal.valueOf(addToCartRequestDto.getQuantity()*inventoryItem.getItem().getPrice()));
        return  cartItem;
    }

    @AfterMapping
    default void mapCartUserId(Cart cart, @MappingTarget CartDto cartDto){
        cartDto.setUserId(cart.getUser().getId());
    }
}
