package com.mifinity.demo.service.domain.model.wrappers;

import com.mifinity.demo.service.domain.models.Card;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class CardTestWrapper {

    private UUID id;
    private String name;
    private UUID accountId;
    private String number;
    private String expiry;

    public static CardTestWrapper buildValidAndNewWithAccountId(final UUID accountId) {
        return CardTestWrapper.builder()
            .name("valid name")
            .accountId(accountId)
            .number("112233")
            .expiry("11/11")
            .build();
    }

    public static CardTestWrapper buildValidAndNewWithAccountIdAndCardNumber(final UUID accountId, final String cardNumber) {
        return CardTestWrapper.builder()
            .name("valid name")
            .accountId(accountId)
            .number(cardNumber)
            .expiry("11/11")
            .build();
    }

    public Card unwrap() {
        return new Card(this.id,
                        this.name,
                        this.accountId,
                        this.number,
                        this.expiry);
    }

}
