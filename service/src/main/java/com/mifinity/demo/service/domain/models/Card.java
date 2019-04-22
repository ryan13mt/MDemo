package com.mifinity.demo.service.domain.models;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String name;

    private UUID accountId;

    private String number;

    private String expiry;

    public Card(@NotNull String name,
                @NotNull UUID accountId,
                @NotNull String number,
                @NotNull String expiry) {
        this.name = name;
        this.accountId = accountId;
        this.number = number;
        this.expiry = expiry;
    }

}
