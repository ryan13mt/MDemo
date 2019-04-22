package com.mifinity.demo.service.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mifinity.demo.service.domain.model.wrappers.CardTestWrapper;
import com.mifinity.demo.service.domain.models.Card;
import com.mifinity.demo.service.domain.models.CardFilter;
import com.mifinity.demo.service.domain.models.User;
import com.mifinity.demo.service.domain.models.UserType;
import com.mifinity.demo.service.domain.services.CardService;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CardController.class, secure = false)
public class CardControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Test
    public void create() throws Exception {
        User userForAuthentication = new User("username", "", null);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userForAuthentication, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        final Card card = CardTestWrapper.buildValidAndNewWithAccountId(userForAuthentication.getId()).unwrap();

        when(cardService.save(any(Card.class))).thenReturn(card);

        this.mockMvc.perform(post("/card")
                                 .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                 .param("name", card.getName())
                                 .param("number", card.getNumber())
                                 .param("expiry", card.getExpiry())
                                 .principal(testingAuthenticationToken))
            .andExpect(status().isCreated());

        verify(cardService, times(1)).save(any(Card.class));
    }

    @Test
    public void findForUser() throws Exception {
        User userForAuthentication = new User("username", "", null);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userForAuthentication, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        when(cardService.filterCardsForUser(any(CardFilter.class))).thenReturn(Collections.singletonList(CardTestWrapper.buildValidAndNewWithAccountId(userForAuthentication.getId()).unwrap()));

        this.mockMvc.perform(get("/card")
                                 .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                 .param("cardNumberFilter", "2")
                                 .principal(testingAuthenticationToken))
            .andExpect(status().isOk());

        verify(cardService, times(1)).filterCardsForUser(any(CardFilter.class));
    }

    @Test
    public void findForAdmin() throws Exception {
        User userForAuthentication = new User("username", "", UserType.ADMIN);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userForAuthentication, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        when(cardService.filterAllCards(any(CardFilter.class))).thenReturn(Collections.singletonList(CardTestWrapper.buildValidAndNewWithAccountId(userForAuthentication.getId()).unwrap()));

        this.mockMvc.perform(get("/card")
                                 .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                 .param("cardNumberFilter", "2")
                                 .principal(testingAuthenticationToken))
            .andExpect(status().isOk());

        verify(cardService, times(1)).filterAllCards(any(CardFilter.class));
    }

}
