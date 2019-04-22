package com.mifinity.demo.service.domain.services;

import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.port.UserDao;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(@Valid @NotNull final String username) {
        return userDao.getUserByUserName(username);
    }

    public User createUser(@Valid @NotNull final User user) {
        try {
            loadUserByUsername(user.getUsername().toLowerCase());
        } catch (EntityNotFoundException e){
            return userDao.createUser(user);
        }
        throw new IllegalStateException("Username already exists");
    }
}
