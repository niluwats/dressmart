package com.nw.dressmart.service;

import com.nw.dressmart.dto.LoginRequestDto;
import com.nw.dressmart.dto.LoginResponseDto;
import com.nw.dressmart.dto.RegisterRequestDto;
import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.mappers.UserMapper;
import com.nw.dressmart.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock JwtService jwtService;

    @Mock VerificationService verificationService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_ShouldSaveUser() {
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

        UserDto userDto = new UserDto(1L, "test", "sata", "asda");
        when(userMapper.UserToUserDto(savedUser)).thenReturn(userDto);

        //when
        UserDto result=authenticationService.saveUser(dto);

        //then
        assertThat(userDto.getEmail()).isEqualTo(result.getEmail());
        assertThat(userDto.getFirstName()).isEqualTo(result.getFirstName());
        assertThat(userDto.getLastName()).isEqualTo(result.getLastName());

        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    void saveUser_ShouldFailWhenEmailAlreadyExists(){
        RegisterRequestDto dto=new RegisterRequestDto("john","doe","mkmk@example.com","password");
        User user=new User(dto.getFirstName(),dto.getLastName(),dto.getEmail(),"encodedPw",Role.USER);

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        assertThrows(IllegalStateException.class,()->
                authenticationService.saveUser(dto),
                "user with email " + dto.getEmail() +" already exists");
    }

    @Test
    void authenticate_ShouldAuthenticateUser() {
        //given
        LoginRequestDto request=new LoginRequestDto("nilu@gmail.com","password");
        User user=new User("nilupulee","wathsala","nilu@gmail.com","encodedPw",Role.USER);
        String token="token";
        LoginResponseDto response=new LoginResponseDto(token);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()))).thenReturn(null);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(token);

        //when
        LoginResponseDto result=authenticationService.authenticate(request);

        //then
        assertThat(response.getToken()).isEqualTo(result.getToken());
    }

    @Test
    void authenticate_ShouldFailWhenEmailNotFound(){
        LoginRequestDto request=new LoginRequestDto("nilu@gmail.com","password");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class,()->
                authenticationService.authenticate(request),
                "user with email " + request.getEmail() +" not found");
    }
}