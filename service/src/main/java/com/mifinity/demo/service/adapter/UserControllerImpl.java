package com.mifinity.demo.service.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.mifinity.demo.api.adapter.controller.UserController;
import com.mifinity.demo.api.adapter.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/games", produces = APPLICATION_JSON_VALUE)
public class UserControllerImpl implements UserController {

    @Override
    public UserDto register(final UserDto userDto) {
        return null;
    }
}
