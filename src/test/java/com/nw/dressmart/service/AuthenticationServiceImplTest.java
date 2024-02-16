package com.nw.dressmart.service;

import com.nw.dressmart.config.TestConfig;
import com.nw.dressmart.dto.RegisterRequestDto;
import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.mappers.UserMapper;
import com.nw.dressmart.repository.CartRepository;
import com.nw.dressmart.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock CartService cartService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled
    void saveUser_ShouldSaveUserAndCreateCard() {
        //given
        RegisterRequestDto dto=new RegisterRequestDto("john","doe","mkmk@example.com","password");

        User user=new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword("encodedPw");
        user.setRole(Role.USER);

        User savedUser=new User();
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword("encodedPw");
        savedUser.setRole(user.getRole());
        savedUser.setId(1L);

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userMapper.registerRequestDtoToUser(dto)).thenReturn(user);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPw");
        when(userRepository.save(user)).thenReturn(savedUser);

//        doNothing().when(cartService).createCart(savedUser.getId());

//        when(userMapper.UserToUserDto(savedUser)).thenReturn(userDto);

        //when
        UserDto result=authenticationService.saveUser(dto);

        //then
        assertThat(dto.getEmail()).isEqualTo(result.getEmail());
        assertThat(dto.getFirstName()).isEqualTo(result.getFirstName());
        assertThat(dto.getLastName()).isEqualTo(result.getLastName());

        verify(userRepository,times(1)).save(any(User.class));
    }

//    @Test
//    @Disabled
//    void authenticate() {
//    }
//
//    @Test
//    @Disabled
//    void verifyToken() {
//    }
}