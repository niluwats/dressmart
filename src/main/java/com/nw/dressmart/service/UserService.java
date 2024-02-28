package com.nw.dressmart.service;

import com.nw.dressmart.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto findUserByEmail(String email);
    UserDto findUser(Long id);
    List<UserDto> findAllUsers();

    String deleteUser(Long id);
}
