package com.nw.dressmart.repository;

import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.entity.VerificationToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VerificationRepositoryTest {
    @Autowired
    VerificationRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByToken_ShouldFindByToken() {
        String token="token";

        User user=new User(1L,"john","doe","john@gmail.com","john1234", Role.CUSTOMER,false,true);

        VerificationToken verificationToken=new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                user
        );

        underTest.save(verificationToken);

        Optional<VerificationToken> expected=underTest.findByToken(token);
        assertThat(expected).isPresent();
    }

    @Test
    void updateVerifiedAt_ShouldUpdateVerifiedAt() {
        String token="token";

        User user=new User(1L,"john","doe","john@gmail.com","john1234", Role.CUSTOMER,false,true);

        VerificationToken verificationToken=new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                user
        );

        underTest.save(verificationToken);

        int updatedRows=underTest.updateVerifiedAt(token,LocalDateTime.now());
        assertThat(updatedRows).isEqualTo(1);

        Optional<VerificationToken> updated=underTest.findByToken(token);
        assertThat(updated).isPresent();
    }
}