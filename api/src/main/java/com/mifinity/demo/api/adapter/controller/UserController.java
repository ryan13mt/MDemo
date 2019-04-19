package com.mifinity.demo.api.adapter.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.mifinity.demo.api.adapter.dto.UserDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/user", produces = APPLICATION_JSON_VALUE)
public interface UserController {

    @PostMapping(path = "/register")
    UserDto register(@PathVariable final UserDto userDto);

}
