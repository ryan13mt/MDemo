package com.mifinity.demo.service.port;

import com.mifinity.demo.service.domain.User;

public interface UserDao {

    User getUserByUserName(final String username);
}
