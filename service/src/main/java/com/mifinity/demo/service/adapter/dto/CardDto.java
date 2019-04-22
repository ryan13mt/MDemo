package com.mifinity.demo.service.adapter.dto;

import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CardDto {

    @NotNull
    @NotEmpty
    private String name;

    private UUID accountId;

    @NotNull
    @NotEmpty
    private String number;

    @NotNull
    @Pattern(regexp = "^\\d{2}\\/(?:\\d{2})")
    private String expiry;
}