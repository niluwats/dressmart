package com.nw.dressmart.service;

import com.nw.dressmart.dto.AddToCartRequestDto;
import com.nw.dressmart.dto.CartDto;
import com.nw.dressmart.dto.CartItemDto;
import com.nw.dressmart.entity.Cart;
import com.nw.dressmart.entity.CartItem;
import com.nw.dressmart.entity.InventoryItem;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.mappers.CartMapper;
import com.nw.dressmart.repository.CartItemRepository;
import com.nw.dressmart.repository.CartRepository;
import com.nw.dressmart.repository.InventoryRepository;
import com.nw.dressmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public void createCart(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->
                new IllegalStateException("user not exists to create a cart"));
        Cart cart=new Cart();
        cart.setUser(user);
        cart.setUpdatedTimestamp(LocalDateTime.now());

        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public CartItemDto addToCart(Long cartId, AddToCartRequestDto request) {
        Cart cart=cartRepository.findById(cartId).orElseThrow(()->
                new IllegalStateException("Cart ID "+cartId+" not found"));

       InventoryItem inventoryItem= inventoryRepository.findById(request.getInventoryItemId()).orElseThrow
               (()-> new IllegalStateException("inventory item ID "+request.getInventoryItemId()+" not found"));

       CartItem existingCartItem=cartItemRepository.findByInventoryItemIdAndCartId(request.getInventoryItemId(),cartId);
       if(existingCartItem!=null){
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity());
            existingCartItem.setSubTotal(existingCartItem.getSubTotal().add(BigDecimal.valueOf(
                    existingCartItem.getInventoryItem().getItem().getPrice()*request.getQuantity()
            )));
            existingCartItem.setUpdatedTimestamp(LocalDateTime.now());

            return cartMapper.cartItemToCartItemDto(existingCartItem);
       }else{
           CartItem cartItem=cartMapper.addToCartDtoToCartItem(request,inventoryItem,cart);
           cartItemRepository.save(cartItem);

           inventoryItem.setQuantity(inventoryItem.getQuantity()-request.getQuantity());

           return cartMapper.cartItemToCartItemDto(cartItem);
       }
    }

    @Override
    public CartDto getCart(Long id) {
        Cart cart=cartRepository.findByCartIdWithActiveItems(id).orElseThrow(()->
                new IllegalStateException("Cart ID "+id+" not found"));

        for(CartItem ci: cart.getCartItems()){
            System.out.println("status "+ci.getStatus());
        }
        return cartMapper.cartToCartDto(cart);
    }

    @Override
    public CartItemDto getCartItem(Long cartItemId) {
        CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(()->
                new IllegalStateException("Cart Item ID "+cartItemId+" not found"));

       return cartMapper.cartItemToCartItemDto(cartItem);
    }

    @Transactional
    @Override
    public void removeFromCart(Long cartId,Long cartItemId) {
        if(!cartRepository.cartContainsCartItem(cartId,cartItemId)){
            throw new IllegalStateException("Cart Item ID "+cartItemId+" does not belongs to cart ");
        }

        CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(()->
                new IllegalStateException("Cart ID "+cartId+" not found"));

        cartItem.setStatus(false);

        InventoryItem inventoryItem= inventoryRepository.findById(cartItem.getInventoryItem().getId()).orElseThrow(()->
                new IllegalStateException("Inventory ID not found"));

        inventoryItem.setQuantity(inventoryItem.getQuantity()+cartItem.getQuantity());
    }
}
