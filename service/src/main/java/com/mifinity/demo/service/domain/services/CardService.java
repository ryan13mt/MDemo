package com.mifinity.demo.service.domain.services;

import com.mifinity.demo.service.domain.models.Card;
import com.mifinity.demo.service.domain.models.CardFilter;
import com.mifinity.demo.service.port.CardDao;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CardService {

    private CardDao cardDao;

    public Optional<Card> findByNumberEquals(@Valid @NotNull final String number) {
        return cardDao.findByNumberEquals(number);
    }

    public Card save(@Valid @NotNull final Card card) {
        final Optional<Card> optionalExistingCard = findByNumberEquals(card.getNumber());

        if (optionalExistingCard.isPresent()) {
            final Card existingCard = optionalExistingCard.get();
            existingCard.setExpiry(card.getExpiry());
            return cardDao.save(existingCard);
        }

        return cardDao.save(card);
    }

    public List<Card> filterCardsForUser(final CardFilter cardFilter) {
        return cardDao.filterCardsForUser(cardFilter);
    }

    public List<Card> filterAllCards(final CardFilter cardFilter) {
        return cardDao.filterAllCards(cardFilter);
    }
}
