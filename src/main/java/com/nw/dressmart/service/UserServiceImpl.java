package com.nw.dressmart.service;

import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.mappers.UserMapper;
import com.nw.dressmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto findUserByEmail(String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("user with email "+email+" not found"));

        return userMapper.UserToUserDto(user);
    }

    @Override
    public UserDto findUser(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->
                new IllegalStateException("user with id "+id+" not found"));

        return userMapper.UserToUserDto(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream().map(userMapper::UserToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).
                orElseThrow(()->
                        new UsernameNotFoundException("user with email "+email+" not found")
                );
    }

}
