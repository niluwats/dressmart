package com.nw.dressmart.service;

import com.nw.dressmart.entity.VerificationToken;
import com.nw.dressmart.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationServiceImpl implements VerificationService{
    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    public void saveVerificationToken(VerificationToken token) {
        verificationRepository.save(token);
    }

    @Override
    public Optional<VerificationToken> getToken(String token) {
        return verificationRepository.findByToken(token);
    }

    @Override
    public void setVerifiedAt(String token) {
        verificationRepository.updateVerifiedAt(token, LocalDateTime.now());
    }
}
