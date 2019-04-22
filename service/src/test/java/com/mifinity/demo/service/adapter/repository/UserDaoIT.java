package com.mifinity.demo.service.adapter.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mifinity.demo.service.domain.model.wrappers.UserTestWrapper;
import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.port.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserDaoIT {

    @Autowired
    private UserDao sut;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void getUserByUserName_shouldReturnExactUser() {
        final User user = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user").unwrap());
        final User retrievedUser = sut.getUserByUserName("user");

        assertThat(retrievedUser).isEqualToComparingFieldByFieldRecursively(user);
    }

    @Test
    public void getUserByUserName_shouldReturnExactUserIgnoringCase() {
        final User user = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user").unwrap());
        final User retrievedUser = sut.getUserByUserName("UsEr");

        assertThat(retrievedUser).isEqualToComparingFieldByFieldRecursively(user);
    }

    @Test
    public void createValidUser_shouldCreateUser() {
        final User createdUser = sut.createUser(UserTestWrapper.buildValidAndNewWithUsername("user").unwrap());
        final User retrievedUser = sut.getUserByUserName("user");

        assertThat(retrievedUser).isEqualToComparingFieldByFieldRecursively(createdUser);
    }

    @Test(expected = IllegalStateException.class)
    public void createUser_usernameAlreadyInDatabase_shouldThrowIllegalStateException() {
        sut.createUser(UserTestWrapper.buildValidAndNewWithUsername("user").unwrap());
        sut.createUser(UserTestWrapper.buildValidAndNewWithUsername("user").unwrap());
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public UserDao cardDao(final UserRepository userRepository) {
            return new UserDaoImpl(userRepository);
        }
    }

}
