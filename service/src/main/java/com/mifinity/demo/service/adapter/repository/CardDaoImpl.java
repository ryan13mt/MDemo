package com.mifinity.demo.service.adapter.repository;

import com.mifinity.demo.service.domain.models.Card;
import com.mifinity.demo.service.domain.models.CardFilter;
import com.mifinity.demo.service.port.CardDao;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class CardDaoImpl implements CardDao {

    private final CardRepository cardRepository;

    @Override
    public Optional<Card> findByNumberEquals(final String number) {
        return cardRepository.findByNumberEquals(number);
    }

    @Override
    public Card save(final Card card) {
        return cardRepository.save(card);
    }

    @Override
    public List<Card> filterCardsForUser(final CardFilter cardFilter) {
        return cardRepository.findByNumberContainingAndAccountIdEquals(cardFilter.getCardNumberFilter() == null ? "" : cardFilter.getCardNumberFilter(),
                                                                       cardFilter.getAccountId());
    }

    @Override
    public List<Card> filterAllCards(final CardFilter cardFilter) {
        return cardRepository.findAllByNumberContaining(cardFilter.getCardNumberFilter() == null ? "" : cardFilter.getCardNumberFilter());
    }
}
