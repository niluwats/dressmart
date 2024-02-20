package com.nw.dressmart.service;

import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.mappers.UserMapper;
import com.nw.dressmart.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUserByEmail_ShouldReturnUserIfExists() {
        //given
        String email="john@example.com";
        User user=new User(1L,"john","doe",email,"john1234",Role.USER,false,true);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto expectedUserDto=new UserDto(1L,"john","doe",email);
        when(userMapper.UserToUserDto(user)).thenReturn(expectedUserDto);

        //when
        UserDto resultUserDto=userService.findUserByEmail(email);

        //then
        assertThat(resultUserDto).isEqualTo(expectedUserDto);
    }

    @Test
    void findUserByEmail_ShouldThrowExceptionIfNotExists() {
        //given
        String email="john@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when and then
        assertThatThrownBy(()->
                userService.findUserByEmail(email)).isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("user with email "+email+" not found");
    }

    @Test
    void findUser_ShouldReturnUserIfExists() {
        //given
        Long userId=1L;
        User user=new User(userId,"john","doe","john@example.com","john1234",Role.USER,false,true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto expectedUserDto=new UserDto(userId,"john","doe","john@example.com");
        when(userMapper.UserToUserDto(user)).thenReturn(expectedUserDto);

        //when
        UserDto resultUserDto=userService.findUser(userId);

        //then
        assertThat(resultUserDto).isEqualTo(expectedUserDto);
    }

    @Test
    void findUser_ShouldThrowExceptionIfNotExists() {
        //given
        Long userId=1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when and then
        assertThatThrownBy(()->userService.findUser(userId)).isInstanceOf(IllegalStateException.class)
                .hasMessage("user with id "+userId+" not found");
    }

    @Test
    void findAllUsers_ShouldReturnListOfUsers() {
        //given
        User user1=new User("john","doe","john@example.com","john123", Role.USER);
        User user2=new User("alice","smith","alice@example.com","alice!123", Role.USER);
        List<User> userList= Arrays.asList(user1,user2);

        when(userRepository.findAll()).thenReturn(userList);

        UserDto userDto1=new UserDto(1L,"john","doe","john@example.com");
        UserDto userDto2=new UserDto(2L,"alice","smith","smith@example.com");
        List<UserDto> expectedUserDtoList=Arrays.asList(userDto1,userDto2);

        when(userMapper.UserToUserDto(user1)).thenReturn(userDto1);
        when(userMapper.UserToUserDto(user2)).thenReturn(userDto2);

        //when
        List<UserDto> resultUserDtoList=userService.findAllUsers();

        //then
        assertThat(resultUserDtoList).isEqualTo(expectedUserDtoList);
        verify(userRepository,times(1)).findAll();
    }

    @Test
    void loadUserByUsername_ShouldReturnUserIfExists() {
        //given
        String email="john@example.com";
        User user=new User(1L,"john","doe",email,"john1234",Role.USER,false,true);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //when
        UserDetails userDetails=userService.loadUserByUsername(email);

        //then
        assertThat(userDetails.getUsername()).isEqualTo(email);
    }

    @Test
    void loadUserByUsername_ShouldThrowExceptionIfNotExists() {
        //given
        String email="john@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()->
                userService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("user with email "+email+" not found");
    }
}