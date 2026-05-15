package com.bank.transfer_service.audit.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for AuditLogDocument.
 * Extended by AuditPersistenceAdapter — not used directly by application layer.
 */
public interface AuditLogRepository extends MongoRepository<AuditLogDocument, String> {

    List<AuditLogDocument> findByCorrelationId(String correlationId);
}
