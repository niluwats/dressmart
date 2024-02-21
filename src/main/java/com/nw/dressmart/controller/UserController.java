package com.nw.dressmart.controller;

import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Long id){
        return ResponseEntity.ok(userService.findUser(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
