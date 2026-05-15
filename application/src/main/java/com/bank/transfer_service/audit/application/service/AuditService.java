package com.bank.transfer_service.audit.application.service;

import com.bank.transfer_service.audit.application.port.in.QueryAuditLogUseCase;
import com.bank.transfer_service.audit.api.dto.AuditLogResponse;

import java.util.List;

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

	@Override
	public List<AuditLogResponse> findByCorrelationId(String correlationId) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<AuditLogResponse> findAll(int page, int size) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
