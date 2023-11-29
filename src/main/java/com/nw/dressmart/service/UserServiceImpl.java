package com.nw.dressmart.service;

import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserDto findUserByEmail(String email) {
        Optional<User> user=userRepository.findByEmail(email);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findUser(Long id) {
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            throw new IllegalStateException("user with id "+id+" not found");
        }
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).
                orElseThrow(()->
                        new UsernameNotFoundException("user with email "+email+" not found")
                );
    }

    private UserDto convertToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
