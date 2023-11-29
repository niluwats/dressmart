package com.nw.dressmart.controller;

import com.nw.dressmart.dto.LoginRequest;
import com.nw.dressmart.dto.LoginResponse;
import com.nw.dressmart.dto.RegisterRequest;
import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest request){
        return new ResponseEntity<UserDto>(authService.saveUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token){
        return ResponseEntity.ok(authService.verifyToken(token));
    }
}
