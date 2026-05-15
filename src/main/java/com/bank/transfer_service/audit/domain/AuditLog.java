package com.bank.transfer_service.audit.domain;

import java.time.Instant;

/**
 * AuditLog aggregate root.
 *
 * Immutable record of a significant banking event.
 * Created by the audit service upon consuming Kafka events.
 * Persisted exclusively for compliance and observability.
 */
public class AuditLog {

    private String id;
    private AuditEventType eventType;
    private String correlationId;    // transferId or accountId
    private String actorId;          // system or user identifier
    private String payload;          // JSON snapshot of the event
    private Instant occurredAt;
    private Instant recordedAt;
}
