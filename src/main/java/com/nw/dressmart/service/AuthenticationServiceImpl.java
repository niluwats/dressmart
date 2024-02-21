package com.nw.dressmart.service;

import com.nw.dressmart.dto.LoginRequestDto;
import com.nw.dressmart.dto.LoginResponseDto;
import com.nw.dressmart.dto.RegisterRequestDto;
import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.mappers.UserMapper;
import com.nw.dressmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private  JwtService jwtService;

    @Override
    public UserDto saveUser(RegisterRequestDto request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("user with email '" + request.getEmail() + " already exists");
        }

        User user=userMapper.registerRequestDtoToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        User savedUser=userRepository.save(user);

        verificationService.saveVerificationToken(user);

        return userMapper.UserToUserDto(savedUser);
    }

    @Override
    public LoginResponseDto authenticate(LoginRequestDto request) {
        String email = request.getEmail();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(email).orElseThrow(()->
                new IllegalStateException("user with email " + email + " not found"));

        String token = jwtService.generateToken(user);
        return new LoginResponseDto(token);
    }
}
