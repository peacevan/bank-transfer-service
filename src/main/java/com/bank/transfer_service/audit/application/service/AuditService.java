package com.bank.transfer_service.audit.application.service;

import com.bank.transfer_service.audit.application.port.in.QueryAuditLogUseCase;

/**
 * Application service implementing all audit use cases.
 *
 * Triggered by:
 *  - TransferEventConsumer (Kafka) → calls SaveAuditLogPort
 *  - AuditController (REST)        → calls LoadAuditLogPort
 *
 * Must NOT depend on Spring Web or infrastructure classes.
 */
public class AuditService implements QueryAuditLogUseCase {
}
