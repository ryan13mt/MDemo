package com.mifinity.demo.service.port;

import com.mifinity.demo.service.domain.models.User;

public interface UserDao {

    User getUserByUserName(final String username);

    User createUser(final User user);
}
