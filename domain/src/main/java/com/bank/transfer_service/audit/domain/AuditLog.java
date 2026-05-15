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

    public AuditLog(String id,
                    AuditEventType eventType,
                    String correlationId,
                    String actorId,
                    String payload,
                    Instant occurredAt,
                    Instant recordedAt) {
        this.id = id;
        this.eventType = eventType;
        this.correlationId = correlationId;
        this.actorId = actorId;
        this.payload = payload;
        this.occurredAt = occurredAt;
        this.recordedAt = recordedAt;
    }

    public String getId() { return id; }
    public AuditEventType getEventType() { return eventType; }
    public String getCorrelationId() { return correlationId; }
    public String getActorId() { return actorId; }
    public String getPayload() { return payload; }
    public Instant getOccurredAt() { return occurredAt; }
    public Instant getRecordedAt() { return recordedAt; }
}
