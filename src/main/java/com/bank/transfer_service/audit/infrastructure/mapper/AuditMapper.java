package com.bank.transfer_service.audit.infrastructure.mapper;

import com.bank.transfer_service.audit.domain.AuditLog;
import com.bank.transfer_service.audit.infrastructure.persistence.AuditLogDocument;

/**
 * Bidirectional mapper between AuditLog (domain) and AuditLogDocument (persistence).
 * Keeps domain objects free from MongoDB annotations.
 */
public class AuditMapper {

    public AuditLogDocument toDocument(AuditLog auditLog) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public AuditLog toDomain(AuditLogDocument document) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
