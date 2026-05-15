package com.bank.transfer_service.audit.api;

import com.bank.transfer_service.audit.application.port.in.QueryAuditLogUseCase;

/**
 * REST adapter — exposes Audit endpoints (read-only).
 *
 * Endpoints:
 *  GET  /api/v1/audit                          → QueryAuditLogUseCase.findAll
 *  GET  /api/v1/audit/correlation/{id}         → QueryAuditLogUseCase.findByCorrelationId
 *
 * Depends only on inbound ports (never on AuditService directly).
 */
public class AuditController {

    private final QueryAuditLogUseCase queryAuditLogUseCase;

    public AuditController(QueryAuditLogUseCase queryAuditLogUseCase) {
        this.queryAuditLogUseCase = queryAuditLogUseCase;
    }
}
