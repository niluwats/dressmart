package com.nw.dressmart.service;

import com.nw.dressmart.dto.LoginRequest;
import com.nw.dressmart.dto.LoginResponse;
import com.nw.dressmart.dto.RegisterRequest;
import com.nw.dressmart.dto.UserDto;

public interface AuthenticationService {
    UserDto saveUser(RegisterRequest request);

    LoginResponse authenticate(LoginRequest request);

    public String verifyToken(String token);
}
