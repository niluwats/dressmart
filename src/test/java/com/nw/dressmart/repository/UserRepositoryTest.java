package com.nw.dressmart.repository;

import com.nw.dressmart.config.TestConfig;
import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    private User user;

    @BeforeEach
    void setUp() {
        user=new User(
                20L,
                "nilupulee",
                "wathsala",
                "nw@gmail.com",
                passwordEncoder.encode("nilupulee&123"),
                Role.CUSTOMER,
                true,
                false
        );

        underTest.save(user);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {
        //given
        String email="nw@gmail.com";

        //when
        Optional<User> expected=underTest.findByEmail(email);

        //then
        assertThat(expected).isPresent();
    }

    @Test
    void itShouldCheckIfUserEmailNotExists() {
        //given
        String email="nilu@gmail.com";

        //when
        Optional<User> expected=underTest.findByEmail(email);

        //then
        assertThat(expected).isEmpty();
    }

    @Test
    void itShouldEnableUser() {
        Long id=20L;
        Optional<User> savedUser=underTest.findById(user.getId());
        assertThat(savedUser).isPresent();

        savedUser.get().setLocked(false);
        savedUser.get().setEnabled(true);

        User enabledUser=underTest.save(savedUser.get());

        assertThat(enabledUser.getEnabled()).isEqualTo(true);
        assertThat(enabledUser.getLocked()).isEqualTo(false);
    }
}