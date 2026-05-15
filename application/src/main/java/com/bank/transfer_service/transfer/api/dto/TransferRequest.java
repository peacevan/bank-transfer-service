package com.bank.transfer_service.transfer.api.dto;

import java.math.BigDecimal;

public record TransferRequest(
        String sourceAccountId,
        String targetAccountId,
        BigDecimal amount,
        String currency,
        String type,
        String description
) {
}
