package com.nw.dressmart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "email_verification_tokens")
public class VerificationToken {
    public VerificationToken() {
    }

    @Id
    @SequenceGenerator(
            name = "verification_token_sequence",
            sequenceName = "verification_token_sequence",
            initialValue = 20
    )
    @GeneratedValue(
            generator = "verification_token_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @NonNull
    private String token;

    @NonNull
    private LocalDateTime createdAt;

    @NonNull
    private LocalDateTime expireAt;
    private LocalDateTime verifiedAt;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    @NonNull
    private User user;
}
