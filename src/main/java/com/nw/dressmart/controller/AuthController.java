package com.nw.dressmart.controller;

import com.nw.dressmart.dto.*;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.service.AuthenticationService;
import com.nw.dressmart.service.VerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authService;

    @Autowired
    private VerificationService verificationService;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequestDto request){
        return new ResponseEntity<UserDto>(authService.saveUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token){
        return ResponseEntity.ok(verificationService.verifyToken(token));
    }

    @PostMapping("/resend")
    public ResponseEntity<String>resendEmail(@Valid @RequestBody SendEmailDto sendEmailDto){
        return ResponseEntity.ok( verificationService.updateVerificationToken(sendEmailDto.getEmail()));
    }
}
