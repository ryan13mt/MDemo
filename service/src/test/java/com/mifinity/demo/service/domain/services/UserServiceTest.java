package com.mifinity.demo.service.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.mifinity.demo.service.domain.model.wrappers.UserTestWrapper;
import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.port.UserDao;
import javax.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService sut;

    @Test
    public void testNewUserCreate() {
        final User user = UserTestWrapper.buildValidAndNewWithUsername("username").unwrap();
        given(userDao.getUserByUserName(any())).willThrow(EntityNotFoundException.class);
        given(userDao.createUser(any(User.class))).willReturn(user);

        final User savedUser = sut.createUser(user);

        assertThat(savedUser).isEqualTo(user);

        final ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao, times(1)).getUserByUserName(user.getUsername());
        verify(userDao, times(1)).createUser(userArgumentCaptor.capture());
    }

    @Test(expected = IllegalStateException.class)
    public void testNewUserCreate_usernameFound_throwIllegalStateException() {
        final User user = UserTestWrapper.buildValidAndNewWithUsername("username").unwrap();
        given(userDao.getUserByUserName(any())).willReturn(user);

        final User savedUser = sut.createUser(user);

        assertThat(savedUser).isEqualTo(user);

        final ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao, times(1)).getUserByUserName(user.getUsername());
        verify(userDao, times(1)).createUser(userArgumentCaptor.capture());
    }

}
