package com.bank.transfer_service.audit.api.dto;

import java.time.Instant;

public record AuditLogResponse(
        String id,
        String eventType,
        String correlationId,
        String actorId,
        String payload,
        Instant occurredAt,
        Instant recordedAt
) {
}
