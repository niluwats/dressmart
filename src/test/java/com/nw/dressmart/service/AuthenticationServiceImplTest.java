package com.nw.dressmart.service;

import com.nw.dressmart.config.TestConfig;
import com.nw.dressmart.dto.RegisterRequestDto;
import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.mappers.UserMapper;
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
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println(passwordEncoder);
        passwordEncoder=new BCryptPasswordEncoder();
    }

    @Test
    @Disabled
    void saveUser_ShouldSaveUserAndCreateCard() {
        //given
        String password="john!123";
        RegisterRequestDto requestDto=new RegisterRequestDto("john","doe","john@example.com",password);
        User mappedUser=new User();
        mappedUser.setPassword(password);
        mappedUser.setRole(Role.USER);

        UserDto userDto=new UserDto(1L,"john","doe","john@example.com");

        when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());
        when(userMapper.registerRequestDtoToUser(requestDto)).thenReturn(mappedUser);
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPw");
        when(userRepository.save(any(User.class))).thenReturn(mappedUser);
        when(userMapper.UserToUserDto(mappedUser)).thenReturn(userDto);

        //when
        UserDto result=authenticationService.saveUser(requestDto);

        //then
        assertThat(result).isNull();
        assertThat(requestDto.getEmail()).isEqualTo(result.getEmail());
        assertThat(requestDto.getFirstName()).isEqualTo(result.getFirstName());
        assertThat(requestDto.getLastName()).isEqualTo(result.getLastName());

        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    @Disabled
    void authenticate() {
    }

    @Test
    @Disabled
    void verifyToken() {
    }
}