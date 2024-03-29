package com.nw.dressmart.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final static Logger LOGGER= LoggerFactory.getLogger(EmailService.class);

    @Override
    @Async
    public void send(String email, String content) {
        try{
            MimeMessage mimeMessage=mailSender.createMimeMessage();

            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setText(content,true);
            helper.setTo(email);
            helper.setSubject("Confirm your email");
            helper.setFrom(fromEmail);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("failed to send email "+e);
            throw new IllegalStateException("failed to send email ",e);
        }catch (Exception e){
            LOGGER.error("unexpected error while sending email ",e);
            throw new IllegalStateException("unexpected error while sending email ",e);
        }
    }
}
