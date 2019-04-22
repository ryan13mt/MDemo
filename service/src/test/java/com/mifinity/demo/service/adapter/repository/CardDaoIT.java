package com.mifinity.demo.service.adapter.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mifinity.demo.service.domain.model.wrappers.CardTestWrapper;
import com.mifinity.demo.service.domain.model.wrappers.UserTestWrapper;
import com.mifinity.demo.service.domain.models.Card;
import com.mifinity.demo.service.domain.models.CardFilter;
import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.port.CardDao;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CardDaoIT {

    @Autowired
    private CardDao sut;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByNumberEquals_shouldFindExactCard() {
        final User user = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user").unwrap());
        final Card card = entityManager.persistAndFlush(CardTestWrapper.buildValidAndNewWithAccountId(user.getId()).unwrap());

        final Card retrievedCard = sut.findByNumberEquals(card.getNumber()).get();
        assertThat(retrievedCard).isEqualToComparingFieldByFieldRecursively(card);
    }

    @Test
    public void save_shouldSaveCard() {
        final User user = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user").unwrap());

        final Card savedCard = sut.save(CardTestWrapper.buildValidAndNewWithAccountId(user.getId()).unwrap());
        final Card retrievedCard = sut.findByNumberEquals(savedCard.getNumber()).get();

        assertThat(retrievedCard).isEqualToComparingFieldByFieldRecursively(savedCard);
    }

    @Test
    public void filterCardsForUser_shouldReturnCards() {
        final User user = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user").unwrap());
        final Card card1 = entityManager.persistAndFlush(CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(user.getId(), "11").unwrap());
        final Card card2 = entityManager.persistAndFlush(CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(user.getId(), "22").unwrap());

        final List<Card> cardsForUser = sut.filterCardsForUser(CardFilter.builder().accountId(user.getId()).build());
        assertThat(cardsForUser).containsExactly(card1, card2);
    }

    @Test
    public void filterCardsForUser_noAccountId_shouldNotReturnCards() {
        final User user = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user").unwrap());
        final Card card1 = entityManager.persistAndFlush(CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(user.getId(), "11").unwrap());

        final List<Card> cardsForUser = sut.filterCardsForUser(CardFilter.builder().build());
        assertThat(cardsForUser).isEmpty();
    }

    @Test
    public void filterCardsForUser_shouldReturnCardsOnlyForParticularUser() {
        final User user1 = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user1").unwrap());
        final User user2 = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user2").unwrap());
        final Card card1 = entityManager.persistAndFlush(CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(user1.getId(), "11").unwrap());
        final Card card2 = entityManager.persistAndFlush(CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(user1.getId(), "22").unwrap());
        final Card card3 = entityManager.persistAndFlush(CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(user2.getId(), "33").unwrap());

        final List<Card> cardsForUser1 = sut.filterCardsForUser(CardFilter.builder().accountId(user1.getId()).build());
        assertThat(cardsForUser1).containsExactly(card1, card2);
        final List<Card> cardsForUser2 = sut.filterCardsForUser(CardFilter.builder().accountId(user2.getId()).build());
        assertThat(cardsForUser2).containsExactly(card3);
    }

    @Test
    public void filterAllCards_shouldReturnAllCards() {
        final User user1 = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user1").unwrap());
        final User user2 = entityManager.persistAndFlush(UserTestWrapper.buildValidAndNewWithUsername("user2").unwrap());
        final Card card1 = entityManager.persistAndFlush(CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(user1.getId(), "11").unwrap());
        final Card card2 = entityManager.persistAndFlush(CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(user2.getId(), "22").unwrap());

        final List<Card> cardsForUser = sut.filterAllCards(CardFilter.builder().build());
        assertThat(cardsForUser).containsExactly(card1, card2);
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public CardDao cardDao(final CardRepository cardRepository) {
            return new CardDaoImpl(cardRepository);
        }
    }

}
