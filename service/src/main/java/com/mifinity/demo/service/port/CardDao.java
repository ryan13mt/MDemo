package com.mifinity.demo.service.port;

import com.mifinity.demo.service.domain.models.Card;
import com.mifinity.demo.service.domain.models.CardFilter;
import java.util.List;
import java.util.Optional;

public interface CardDao {

    Optional<Card> findByNumberEquals(String number);

    Card save(Card card);

    List<Card> filterCardsForUser(CardFilter cardFilter);

    List<Card> filterAllCards(CardFilter cardFilter);
}
