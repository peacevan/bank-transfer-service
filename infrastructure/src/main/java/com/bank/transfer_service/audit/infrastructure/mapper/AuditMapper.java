package com.bank.transfer_service.audit.infrastructure.mapper;

import com.bank.transfer_service.audit.domain.AuditLog;
import com.bank.transfer_service.audit.infrastructure.persistence.AuditLogDocument;
import org.springframework.stereotype.Component;
import com.bank.transfer_service.audit.domain.AuditEventType;
import java.time.Instant;

/**
 * Bidirectional mapper between AuditLog (domain) and AuditLogDocument (persistence).
 * Keeps domain objects free from MongoDB annotations.
 */
@Component
public class AuditMapper {

    public AuditMapper() {}

    public AuditLogDocument toDocument(AuditLog auditLog) {
        if (auditLog == null) return null;
        AuditLogDocument d = new AuditLogDocument();
        d.setId(auditLog.getId());
        d.setEventType(auditLog.getEventType() != null ? auditLog.getEventType().name() : null);
        d.setCorrelationId(auditLog.getCorrelationId());
        d.setActorId(auditLog.getActorId());
        d.setPayload(auditLog.getPayload());
        d.setOccurredAt(auditLog.getOccurredAt());
        d.setRecordedAt(auditLog.getRecordedAt());
        return d;
    }

    public AuditLog toDomain(AuditLogDocument document) {
        if (document == null) return null;
        AuditEventType type = document.getEventType() != null ? AuditEventType.valueOf(document.getEventType()) : null;
        Instant occurred = document.getOccurredAt();
        Instant recorded = document.getRecordedAt();
        return new AuditLog(document.getId(), type, document.getCorrelationId(), document.getActorId(), document.getPayload(), occurred, recorded);
    }
}
