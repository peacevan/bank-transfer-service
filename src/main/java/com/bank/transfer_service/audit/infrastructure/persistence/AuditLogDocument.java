package com.bank.transfer_service.audit.infrastructure.persistence;

import java.time.Instant;

/**
 * MongoDB document mapping for the "audit_logs" collection.
 * Annotated with @Document(collection = "audit_logs").
 * Never exposed outside the infrastructure layer.
 */
public class AuditLogDocument {

    private String id;
    private String eventType;
    private String correlationId;
    private String actorId;
    private String payload;
    private Instant occurredAt;
    private Instant recordedAt;
}
