package com.bank.transfer_service.account.api.dto;

import java.math.BigDecimal;

public record AccountResponse(
        String accountId,
        String ownerName,
        BigDecimal balance,
        String currency,
        String status
) {
}
