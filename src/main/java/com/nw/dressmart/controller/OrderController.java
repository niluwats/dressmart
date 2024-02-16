package com.nw.dressmart.controller;

import com.nw.dressmart.dto.OrderRequestDto;
import com.nw.dressmart.service.JwtService;
import com.nw.dressmart.service.OrderService;
import com.nw.dressmart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;
//
//    @PostMapping()
//    private ResponseEntity<String> placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto){
//        return ResponseEntity.ok(orderService.newOrder(orderRequestDto));
//    }

    private Long getUserId(String authHeader){
        String token=authHeader.substring(7);
        String email=jwtService.extractUsername(token);
        return userService.findUserByEmail(email).getId();
    }
}
