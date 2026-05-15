package com.bank.transfer_service.audit.application.service;

import com.bank.transfer_service.audit.application.port.in.QueryAuditLogUseCase;
import com.bank.transfer_service.audit.application.port.out.LoadAuditLogPort;
import com.bank.transfer_service.audit.api.dto.AuditLogResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Application service implementing all audit use cases.
 */
public class AuditService implements QueryAuditLogUseCase {

	private final LoadAuditLogPort loadAuditLogPort;

	public AuditService(LoadAuditLogPort loadAuditLogPort) {
		this.loadAuditLogPort = loadAuditLogPort;
	}

	@Override
	public List<AuditLogResponse> findByCorrelationId(String correlationId) {
		return loadAuditLogPort.findByCorrelationId(correlationId).stream()
				.map(a -> new AuditLogResponse(
						a.getId(),
						a.getEventType() != null ? a.getEventType().name() : null,
						a.getCorrelationId(),
						a.getActorId(),
						a.getPayload(),
						a.getOccurredAt(),
						a.getRecordedAt()
				))
				.collect(Collectors.toList());
	}

	@Override
	public List<AuditLogResponse> findAll(int page, int size) {
		return loadAuditLogPort.findAll(page, size).stream()
				.map(a -> new AuditLogResponse(
						a.getId(),
						a.getEventType() != null ? a.getEventType().name() : null,
						a.getCorrelationId(),
						a.getActorId(),
						a.getPayload(),
						a.getOccurredAt(),
						a.getRecordedAt()
				))
				.collect(Collectors.toList());
	}
}
