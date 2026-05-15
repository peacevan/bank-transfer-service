package com.bank.transfer_service.account.api.dto;

import java.math.BigDecimal;

/**
 * Inbound DTO — payload for POST /api/v1/accounts.
 * Validated with Bean Validation annotations (@NotBlank, @NotNull, etc.).
 */
public record CreateAccountRequest(
        String ownerName,
        String ownerDocument,
        BigDecimal initialBalance,
        String currency
) {
}
