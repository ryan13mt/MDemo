package com.mifinity.demo.service.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.mifinity.demo.service.adapter.dto.UserDto;
import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.domain.services.UserService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/user", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDto register(@Valid @NotNull final UserDto user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new IllegalStateException("Passwords to not match");
        }

        final User newUser = userService.createUser(new User(user.getUsername().toLowerCase(),
                                                             user.getPassword(),
                                                             null));
        return new UserDto(newUser.getUsername(),
                           newUser.getPassword(),
                           null);
    }
}
