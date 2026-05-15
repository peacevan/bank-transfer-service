package com.bank.transfer_service.transfer.api.dto;

import java.math.BigDecimal;

/**
 * Inbound DTO — payload for POST /api/v1/transfers.
 * Validated with Bean Validation annotations.
 */
public record TransferRequest(
        String sourceAccountId,
        String targetAccountId,
        BigDecimal amount,
        String currency,
        String type,          // PIX | TED | DOC | INTERNAL
        String description
) {
}
