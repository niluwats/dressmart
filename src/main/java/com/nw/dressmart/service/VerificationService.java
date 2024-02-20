package com.nw.dressmart.service;

import com.nw.dressmart.entity.User;
import com.nw.dressmart.entity.VerificationToken;

import java.util.Optional;

public interface VerificationService {
    void saveVerificationToken(User user);

    Optional<VerificationToken> getToken(String token);

    String verifyToken(String token);
}
