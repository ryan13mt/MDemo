package com.mifinity.demo.service.adapter.repository;

import com.mifinity.demo.service.domain.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("select user from User user where lower(user.username) = lower(:username)")
    Optional<User> findByUsernameIgnoreCase(final String username);

    User save(final User user);
}
