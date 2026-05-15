package com.bank.transfer_service.transfer.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record TransferResponse(
        String transferId,
        String sourceAccountId,
        String targetAccountId,
        BigDecimal amount,
        String currency,
        String type,
        String status,
        Instant createdAt,
        Instant processedAt
) {
}
