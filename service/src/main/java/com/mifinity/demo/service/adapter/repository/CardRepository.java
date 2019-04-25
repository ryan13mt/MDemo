package com.mifinity.demo.service.adapter.repository;

import com.mifinity.demo.service.domain.models.Card;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {

    Optional<Card> findByNumberEquals(final String cardNumberFilter);

    List<Card> findAllByNumberContaining(final String cardNumberFilter);

    List<Card> findByNumberContainingAndAccountIdEquals(final String cardNumberFilter, final UUID accountId);

}
