package com.bank.transfer_service.audit.infrastructure.persistence;

import com.bank.transfer_service.audit.domain.AuditLog;
import com.bank.transfer_service.audit.application.port.out.SaveAuditLogPort;
import com.bank.transfer_service.audit.application.port.out.LoadAuditLogPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AuditPersistenceAdapter implements SaveAuditLogPort, LoadAuditLogPort {

	private final AuditLogRepository repository;
	private final com.bank.transfer_service.audit.infrastructure.mapper.AuditMapper mapper;

	public AuditPersistenceAdapter(AuditLogRepository repository, com.bank.transfer_service.audit.infrastructure.mapper.AuditMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public AuditLog save(AuditLog auditLog) {
		if (auditLog == null) return null;
		AuditLogDocument doc = mapper.toDocument(auditLog);
		AuditLogDocument saved = repository.save(doc);
		return mapper.toDomain(saved);
	}

	@Override
	public List<AuditLog> findByCorrelationId(String correlationId) {
		return repository.findByCorrelationId(correlationId).stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<AuditLog> findAll(int page, int size) {
		return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
	}
}
