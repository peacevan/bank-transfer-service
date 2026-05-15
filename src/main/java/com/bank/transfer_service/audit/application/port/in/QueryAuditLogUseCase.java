package com.bank.transfer_service.audit.application.port.in;

import com.bank.transfer_service.audit.api.dto.AuditLogResponse;

import java.util.List;

/**
 * Inbound port — queries the audit log.
 * Supports filtering by correlationId and eventType.
 * Implemented by AuditService (application layer).
 */
public interface QueryAuditLogUseCase {

    List<AuditLogResponse> findByCorrelationId(String correlationId);

    List<AuditLogResponse> findAll(int page, int size);
}
