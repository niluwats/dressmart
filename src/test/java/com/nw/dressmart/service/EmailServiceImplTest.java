package com.nw.dressmart.service;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {
    @Mock
    JavaMailSender javaMailSender;

    @Mock
    GreenMail greenMail;

    @InjectMocks
    EmailServiceImpl emailService;

    @Before
    public void startMailServer(){
        greenMail=new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
    }

    @After
    public void stopMailServer(){
        greenMail.stop();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled
    void send_ShouldSendEmail() {
//        MimeMessage mimeMessage=mock(MimeMessage.class);
//        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//
//        String email="john@gmail.com";
//        String content="email content";
//
//        emailService.send(email,content);
//        verify(javaMailSender,times(1)).send(any(MimeMessage.class));
        String content="content";
        String subject="confirm email";
        String to="kniluwathsala@gmail.com";

        emailService.send(to,content);

        assertTrue(greenMail.waitForIncomingEmail(5000,1));

    }
}