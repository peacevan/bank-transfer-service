package com.bank.transfer_service.account.api.dto;

import java.math.BigDecimal;

/**
 * Outbound DTO — response body for GET /api/v1/accounts/{id}/balance.
 */
public record BalanceResponse(
        String accountId,
        BigDecimal balance,
        String currency
) {
}
