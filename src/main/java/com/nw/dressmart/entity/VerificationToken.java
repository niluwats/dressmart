package com.nw.dressmart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "email_verification_tokens")
public class VerificationToken {
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
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expireAt;
    private LocalDateTime verifiedAt;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
