package com.bank.transfer_service.account.api.dto;

import java.math.BigDecimal;

/**
 * Outbound DTO — response body for account operations.
 */
public record AccountResponse(
        String accountId,
        String ownerName,
        BigDecimal balance,
        String currency,
        String status
) {
}
