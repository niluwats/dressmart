package com.nw.dressmart.service;

import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.entity.VerificationToken;
import com.nw.dressmart.repository.UserRepository;
import com.nw.dressmart.repository.VerificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationServiceImplTest {
    @Mock
    VerificationRepository verificationRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    EmailService emailService;

    @Spy
    @InjectMocks
    VerificationServiceImpl verificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveVerificationToken_ShouldSaveVerificationToken() {
        ReflectionTestUtils.setField(verificationService,"tokenExpiration",5L);
        String token = "tokenexample";

        User user = new User(1L, "john", "doe", "john@gmail.com", "john1234", Role.CUSTOMER, false, true);

        VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                user
        );

        String link = "http://localhost:8080/api/v1/auth/verifyEmail?token=" + token;

        when(verificationService.buildEmail(anyString(),anyString())).thenReturn("email content");
        doNothing().when(emailService).send(user.getEmail(),"email content");
        when(verificationRepository.save(any(VerificationToken.class))).thenReturn(verificationToken);

        verificationService.saveVerificationToken(user);

        verificationToken.setId(1L);
        assertThat(verificationToken.getId()).isEqualTo(1L);
        verify(verificationRepository,times(1)).save(any(VerificationToken.class));
    }

    @Test
    void verifyToken_ShouldVerifyToken() {
        String token="token";
        String msg="Email verified";
        User user=new User("nilupulee","wathsala","nilu@gmail.com","encodedPw",Role.CUSTOMER);
        VerificationToken verificationToken=new VerificationToken(
                token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(5),user);

        when(verificationRepository.findByToken(token)).thenReturn(Optional.of(verificationToken));
        when(verificationRepository.updateVerifiedAt(any(String.class),any(LocalDateTime.class))).thenReturn(1);
        doNothing().when(userRepository).enableUser(user.getEmail());

        String result=verificationService.verifyToken(token);

        assertThat(result).isEqualTo(msg);
    }

    @Test
    void verifyToken_ShouldFailWhenTokenNotFound(){
        String token="token";

        when(verificationRepository.findByToken(token)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,()->verificationService.verifyToken(token),"token not found");
    }

    @Test
    void verifyToken_ShouldFailWhenEmailAlreadyVerified(){
        String token="token";
        User user=new User("nilupulee","wathsala","nilu@gmail.com","encodedPw",Role.CUSTOMER);
        VerificationToken verificationToken=new VerificationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(5),user);
        verificationToken.setVerifiedAt(LocalDateTime.now());

        when(verificationRepository.findByToken(token)).thenReturn(Optional.of(verificationToken));

        assertThrows(IllegalStateException.class,()->verificationService.verifyToken(token),"email already verified");
    }

    @Test
    void verifyToken_ShouldFailWhenLinkExpired(){
        String token="token";
        User user=new User("nilupulee","wathsala","nilu@gmail.com","encodedPw",Role.CUSTOMER);
        VerificationToken verificationToken=new VerificationToken(token, LocalDateTime.now(),LocalDateTime.now().minusMinutes(1),user);

        when(verificationRepository.findByToken(token)).thenReturn(Optional.of(verificationToken));

        assertThrows(IllegalStateException.class,()->verificationService.verifyToken(token),"link expired");
    }
}