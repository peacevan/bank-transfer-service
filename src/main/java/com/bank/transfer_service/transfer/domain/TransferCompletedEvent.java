package com.bank.transfer_service.transfer.domain;

import com.bank.transfer_service.shared.domain.TransferId;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Domain event published after a Transfer is successfully processed.
 * Consumed by the audit feature via Kafka.
 */
public record TransferCompletedEvent(
        TransferId transferId,
        String sourceAccountId,
        String targetAccountId,
        BigDecimal amount,
        String currency,
        Instant occurredAt
) {
}
