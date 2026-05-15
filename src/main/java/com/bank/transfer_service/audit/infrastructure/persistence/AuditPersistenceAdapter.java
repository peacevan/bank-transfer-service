package com.bank.transfer_service.audit.infrastructure.persistence;

import com.bank.transfer_service.audit.domain.AuditLog;
import com.bank.transfer_service.audit.application.port.out.SaveAuditLogPort;
import com.bank.transfer_service.audit.application.port.out.LoadAuditLogPort;

/**
 * MongoDB adapter implementing audit log persistence ports.
 *
 * Bridges domain AuditLog ↔ AuditLogDocument using AuditMapper.
 * Implements: SaveAuditLogPort, LoadAuditLogPort.
 */
public class AuditPersistenceAdapter implements SaveAuditLogPort, LoadAuditLogPort {
}
