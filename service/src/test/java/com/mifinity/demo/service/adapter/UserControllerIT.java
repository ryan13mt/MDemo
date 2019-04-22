package com.mifinity.demo.service.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mifinity.demo.service.adapter.dto.UserDto;
import com.mifinity.demo.service.domain.model.wrappers.UserTestWrapper;
import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.domain.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringRunner.class)
@EnableWebMvc
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void register() throws Exception {
        final UserDto userDto = UserTestWrapper.buildValidAndNewWithUsername("username").toDto();
        final User user = UserTestWrapper.buildValidAndNewWithUsername("username").unwrap();

        when(userService.createUser(any(User.class))).thenReturn(user);

        this.mockMvc.perform(post("/user")
                                 .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                 .param("username", userDto.getUsername())
                                 .param("password", userDto.getPassword())
                                 .param("confirmPassword", userDto.getConfirmPassword()))
            .andExpect(status().isCreated());

        verify(userService, times(1)).createUser(any(User.class));
    }

}
