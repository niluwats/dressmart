package com.nw.dressmart.controller;

import com.nw.dressmart.dto.AddToCartRequestDto;
import com.nw.dressmart.dto.CartDto;
import com.nw.dressmart.dto.CartItemDto;
import com.nw.dressmart.service.CartService;
import com.nw.dressmart.service.JwtService;
import com.nw.dressmart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    @Autowired
    private CartService cartItemService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("{cartId}")
    public ResponseEntity<CartItemDto> addItem(@RequestBody @Valid AddToCartRequestDto request,
                                               @PathVariable("cartId")Long cartId){
        return ResponseEntity.ok(cartItemService.addToCart(cartId,request));
    }

    @GetMapping("{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable("cartId")Long cartId){
        return ResponseEntity.ok(cartItemService.getCart(cartId));
    }
    @GetMapping("/cartItem/{cartItemId}")
    public ResponseEntity<CartItemDto> getCartItem(@PathVariable("cartItemId")Long cartItemId){
        return ResponseEntity.ok(cartItemService.getCartItem(cartItemId));
    }

    @DeleteMapping("/{cartId}/{cartItemId}")
    public String removeCartItem(@PathVariable("cartId")Long cartId,
                                 @PathVariable("cartItemId")Long cartItemId){
        cartItemService.removeFromCart(cartId,cartItemId);
        return "removed";
    }
}
