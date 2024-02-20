package com.nw.dressmart.controller;

import com.nw.dressmart.dto.LoginRequestDto;
import com.nw.dressmart.dto.LoginResponseDto;
import com.nw.dressmart.dto.RegisterRequestDto;
import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    AuthenticationService authenticationService;

    @InjectMocks
    AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldRegisterNewUser() {
        RegisterRequestDto request=new RegisterRequestDto("john","doe","john@gmail.com","john1234");
        UserDto userResponse=new UserDto(1L,"john","doe","john@gmail.com");

        when(authenticationService.saveUser(request)).thenReturn(userResponse);

        ResponseEntity<UserDto> result=authController.register(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void login_ShouldLogin() {
        LoginRequestDto request=new LoginRequestDto("john@gmail.com","john1234");
        LoginResponseDto response=new LoginResponseDto("token");

        when(authenticationService.authenticate(request)).thenReturn(response);

        ResponseEntity<LoginResponseDto> result=authController.login(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}