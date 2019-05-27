package com.mifinity.demo.service.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.mifinity.demo.service.adapter.dto.CardDto;
import com.mifinity.demo.service.domain.models.Card;
import com.mifinity.demo.service.domain.models.CardFilter;
import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.domain.models.UserType;
import com.mifinity.demo.service.domain.services.CardService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/card", produces = APPLICATION_JSON_VALUE)
public class CardController {

    private final CardService cardService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public CardDto createOrUpdate(@AuthenticationPrincipal final Authentication authentication,
                                  @Valid @NotNull final CardDto card) {
        final User user = (User) authentication.getPrincipal();
        final Optional<Card> optionalCard = cardService.findByNumberEquals(card.getNumber());

        if (isNormalUser(user) && (optionalCard.isPresent() && !optionalCard.get().getAccountId().equals(user.getId()))) {
            throw new IllegalStateException("User must have Admin rights to update another users card.");
        }

        final Card newCard = cardService.createOrUpdate(new Card(card.getName(),
                                                                 user.getId(),
                                                                 card.getNumber(),
                                                                 card.getExpiry()));

        return new CardDto(newCard.getName(),
                           newCard.getAccountId(),
                           newCard.getNumber(),
                           newCard.getExpiry());
    }

    @GetMapping
    public List<CardDto> find(@AuthenticationPrincipal final Authentication authentication,
                              final String cardNumberFilter) {
        final User user = (User) authentication.getPrincipal();
        final CardFilter cardFilter = CardFilter.builder().cardNumberFilter(cardNumberFilter).build();

        if (isNormalUser(user)) {
            cardFilter.setAccountId(user.getId());
            return transformCardToCardDto(cardService.filterCardsForUser(cardFilter));
        }

        return transformCardToCardDto(cardService.filterAllCards(cardFilter));

    }

    public List<CardDto> transformCardToCardDto(List<Card> cardList) {

        return cardList.stream().map(card -> new CardDto(card.getName(),
                                                         card.getAccountId(),
                                                         card.getNumber(),
                                                         card.getExpiry())).collect(Collectors.toList());
    }

    private boolean isNormalUser(final User user) {
        return user.getType() == null || !user.getType().equals(UserType.ADMIN);
    }
}
