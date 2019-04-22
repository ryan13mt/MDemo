package com.mifinity.demo.service.domain.models;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardFilter {

    private UUID accountId;
    private String cardNumberFilter;

}
