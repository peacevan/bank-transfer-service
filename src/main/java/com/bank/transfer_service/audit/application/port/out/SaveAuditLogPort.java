package com.bank.transfer_service.audit.application.port.out;

import com.bank.transfer_service.audit.domain.AuditLog;

/**
 * Outbound port — write-side persistence for AuditLog.
 * Implemented by AuditPersistenceAdapter (infrastructure layer).
 */
public interface SaveAuditLogPort {

    AuditLog save(AuditLog auditLog);
}
