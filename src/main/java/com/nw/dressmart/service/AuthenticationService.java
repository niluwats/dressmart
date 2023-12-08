package com.nw.dressmart.service;

import com.nw.dressmart.dto.LoginRequestDto;
import com.nw.dressmart.dto.LoginResponseDto;
import com.nw.dressmart.dto.RegisterRequestDto;
import com.nw.dressmart.dto.UserDto;

public interface AuthenticationService {
    UserDto saveUser(RegisterRequestDto request);

    LoginResponseDto authenticate(LoginRequestDto request);

    public String verifyToken(String token);
}
