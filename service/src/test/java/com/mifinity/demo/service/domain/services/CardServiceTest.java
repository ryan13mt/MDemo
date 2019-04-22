package com.mifinity.demo.service.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.mifinity.demo.service.domain.model.wrappers.CardTestWrapper;
import com.mifinity.demo.service.domain.models.Card;
import com.mifinity.demo.service.port.CardDao;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {

    @Mock
    private CardDao cardDao;

    @InjectMocks
    private CardService sut;

    @Test
    public void testNewCardSave() {
        final Card card = CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(UUID.randomUUID(), "1").unwrap();
        given(cardDao.save(any(Card.class))).willReturn(card);

        final Card savedCard = sut.save(card);

        assertThat(savedCard).isEqualTo(card);

        final ArgumentCaptor<Card> cardArgumentCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardDao, times(1)).findByNumberEquals(card.getNumber());
        verify(cardDao, times(1)).save(cardArgumentCaptor.capture());

        final Card unsavedCard = cardArgumentCaptor.getValue();
        assertThat(unsavedCard.getId()).isEqualTo(savedCard.getId());
        assertThat(unsavedCard.getNumber()).isEqualTo(savedCard.getNumber());
        assertThat(unsavedCard.getExpiry()).isEqualTo(savedCard.getExpiry());
        assertThat(unsavedCard.getName()).isEqualTo(savedCard.getName());
        assertThat(unsavedCard.getAccountId()).isEqualTo(savedCard.getAccountId());
    }

    @Test
    public void testCardUpdate() {
        final UUID accountId = UUID.randomUUID();
        final Card oldCard = CardTestWrapper.buildValidAndNewWithAccountIdAndCardNumber(accountId, "1").unwrap();
        final Card newCard = CardTestWrapper.builder().accountId(accountId).name(oldCard.getName()).number("1").expiry("22/12").build().unwrap();
        given(cardDao.save(any(Card.class))).willReturn(newCard);
        given(cardDao.findByNumberEquals(any())).willReturn(Optional.of(oldCard));

        final Card savedCard = sut.save(newCard);

        assertThat(savedCard.getId()).isEqualTo(oldCard.getId());

        final ArgumentCaptor<Card> cardArgumentCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardDao, times(1)).findByNumberEquals(oldCard.getNumber());
        verify(cardDao, times(1)).save(cardArgumentCaptor.capture());

        final Card unsavedCard = cardArgumentCaptor.getValue();
        assertThat(unsavedCard.getId()).isEqualTo(savedCard.getId());
        assertThat(unsavedCard.getNumber()).isEqualTo(savedCard.getNumber());
        assertThat(unsavedCard.getExpiry()).isEqualTo(savedCard.getExpiry());
        assertThat(unsavedCard.getName()).isEqualTo(savedCard.getName());
        assertThat(unsavedCard.getAccountId()).isEqualTo(savedCard.getAccountId());
    }

}
