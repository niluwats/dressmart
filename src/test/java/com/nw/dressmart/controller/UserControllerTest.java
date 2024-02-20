package com.nw.dressmart.controller;

import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class UserControllerTest {
    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldGetAllUsers() {
        UserDto user1=new UserDto(1L,"john","doe","john@example.com");
        UserDto user2=new UserDto(2L,"emi","womg","emi@example.com");
        UserDto user3=new UserDto(3L,"bob","sad","bob@example.com");

        List<UserDto> users= Arrays.asList(user1,user2,user3);

        when(userService.findAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDto>> result=userController.getAllUsers();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getUser_ShouldGetUserById() {
        UserDto user=new UserDto(1L,"john","doe","john@example.com");
        when(userService.findUser(1L)).thenReturn(user);
        when(userService.findUser(1L)).thenReturn(user);

        ResponseEntity<UserDto> result=userController.getUser(1L);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}