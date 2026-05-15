package com.bank.transfer_service.audit.application.port.out;

import com.bank.transfer_service.audit.domain.AuditLog;

import java.util.List;

/**
 * Outbound port — read-side persistence for AuditLog.
 * Implemented by AuditPersistenceAdapter (infrastructure layer).
 */
public interface LoadAuditLogPort {

    List<AuditLog> findByCorrelationId(String correlationId);

    List<AuditLog> findAll(int page, int size);
}
