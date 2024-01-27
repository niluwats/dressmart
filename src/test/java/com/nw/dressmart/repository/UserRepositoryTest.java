package com.nw.dressmart.repository;

import com.nw.dressmart.config.TestConfig;
import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(TestConfig.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {
        //given
        String email="nw@gmail.com";

        User user=new User(
                "nilupulee",
                "wathsala",
                email,
                passwordEncoder.encode("nilupulee&123"),
                Role.USER
        );

        underTest.save(user);

        //when
        Optional<User> expected=underTest.findByEmail(email);

        //then
        assertThat(expected).isPresent();
    }

    @Test
    void itShouldCheckIfUserEmailNotExists() {
        //given
        String email="nw@gmail.com";

        //when
        Optional<User> expected=underTest.findByEmail(email);

        //then
        assertThat(expected).isEmpty();
    }

    @Test
    void itShouldEnableUser() {
        //given
        String email="nw@gmail.com";

        User user=new User(
                "nilupulee",
                "wathsala",
                email,
                passwordEncoder.encode("nilupulee&123"),
                Role.USER
        );

        underTest.save(user);

        //when
        int updatedRows=underTest.enableUser(email);

        //then
        assertThat(updatedRows).isEqualTo(1);

        Optional<User> enabledUser=underTest.findByEmail(email);
        assertThat(enabledUser).isPresent();
        assertThat(enabledUser.get().isEnabled()).isTrue();
        assertThat(enabledUser.get().isAccountNonLocked()).isTrue();
    }
}