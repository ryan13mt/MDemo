package com.mifinity.demo.service.adapter.repository;

import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.port.UserDao;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    @Override
    public User getUserByUserName(final String userName) {
        return userRepository.findByUsernameIgnoreCase(userName).orElseThrow(() -> new EntityNotFoundException(String.format("User with name %s does not exist.", userName)));
    }

    @Override
    public User createUser(final User user) {
        try {
            getUserByUserName(user.getUsername());
        } catch (EntityNotFoundException e) {
            return userRepository.save(user);
        }
        throw new IllegalStateException("User already exists");
    }
}
