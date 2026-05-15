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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    public String getActorId() { return actorId; }
    public void setActorId(String actorId) { this.actorId = actorId; }
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
    public Instant getOccurredAt() { return occurredAt; }
    public void setOccurredAt(Instant occurredAt) { this.occurredAt = occurredAt; }
    public Instant getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Instant recordedAt) { this.recordedAt = recordedAt; }
}
