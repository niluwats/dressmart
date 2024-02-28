package com.nw.dressmart.service;

import com.nw.dressmart.entity.User;
import com.nw.dressmart.entity.VerificationToken;

import java.util.Optional;

public interface VerificationService {
    void saveVerificationToken(User user);

    String verifyToken(String token);
}
