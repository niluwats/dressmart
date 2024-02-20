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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

//    @Test
//    void verifyToken_shouldVerifyToken() {
//        String token="token";
//        String msg="Email verified";
//        User user=new User("nilupulee","wathsala","nilu@gmail.com","encodedPw",Role.USER);
//        VerificationToken verificationToken=new VerificationToken(
//                token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(5),user);
//
//        when(verificationService.getToken(token)).thenReturn(Optional.of(verificationToken));
//        doNothing().when(verificationService).setVerifiedAt(token);
//        when(userRepository.enableUser(user.getEmail())).thenReturn(1);
//
//        String result=authenticationService.verifyToken(token);
//
//        assertThat(result).isEqualTo(msg);
//    }
}