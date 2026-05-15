package com.bank.transfer_service.account.api.dto;

import java.math.BigDecimal;

public record BalanceResponse(
        String accountId,
        BigDecimal balance,
        String currency
) {
}
