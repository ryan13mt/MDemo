package com.mifinity.demo.service.adapter;

import com.mifinity.demo.service.port.UserDao;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DemoUserDetailsService implements UserDetailsService {

    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(@Valid @NotNull final String username) throws UsernameNotFoundException {
        return userDao.getUserByUserName(username);
    }
}
