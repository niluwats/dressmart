package com.nw.dressmart.service;

import com.nw.dressmart.entity.VerificationToken;

import java.util.Optional;

public interface VerificationService {
    void saveVerificationToken(VerificationToken token);

    Optional<VerificationToken> getToken(String token);

    void setVerifiedAt(String token);
}
