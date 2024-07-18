package com.nw.dressmart.service;

import com.nw.dressmart.entity.User;
import com.nw.dressmart.entity.VerificationToken;

import java.util.Optional;

public interface VerificationService {
    String saveVerificationToken(User user);

    String updateVerificationToken(String email);

    String verifyToken(String token);
}
