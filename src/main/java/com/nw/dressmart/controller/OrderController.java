package com.nw.dressmart.controller;

import com.nw.dressmart.dto.OrderRequestDto;
import com.nw.dressmart.dto.OrderResponse;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.service.JwtService;
import com.nw.dressmart.service.OrderService;
import com.nw.dressmart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody @Valid OrderRequestDto orderRequestDto,
            @AuthenticationPrincipal User user
            ){
        return ResponseEntity.ok(orderService.newOrder(orderRequestDto, user.getId()));
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderResponse>>viewOrders(){
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderResponse> viewOrder(@PathVariable("orderId")Long id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>>viewUserOrders(@PathVariable("userId")Long userId){
        return ResponseEntity.ok(orderService.getOrders(userId));
    }
}
