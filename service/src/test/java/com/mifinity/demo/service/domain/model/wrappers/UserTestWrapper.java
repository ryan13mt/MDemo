package com.mifinity.demo.service.domain.model.wrappers;

import com.mifinity.demo.service.adapter.dto.UserDto;
import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.domain.models.UserType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class UserTestWrapper {

    private UUID id;
    private String username;
    private String password;
    private UserType type;

    public static UserTestWrapper buildValidAndNewWithUsername(final String username) {
        return UserTestWrapper.builder()
            .username(username)
            .password("testPassword")
            .type(null)
            .build();
    }

    public User unwrap() {
        return new User(this.username,
                        this.password,
                        this.type);
    }

    public UserDto toDto() {
        return new UserDto(this.username,
                           this.password,
                           this.password);
    }

}
