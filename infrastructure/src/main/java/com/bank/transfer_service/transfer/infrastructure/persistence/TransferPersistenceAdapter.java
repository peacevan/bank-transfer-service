package com.bank.transfer_service.transfer.infrastructure.persistence;

import com.bank.transfer_service.transfer.domain.Transfer;
import com.bank.transfer_service.transfer.application.port.out.LoadTransferPort;
import com.bank.transfer_service.transfer.application.port.out.SaveTransferPort;
import com.bank.transfer_service.shared.domain.TransferId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TransferPersistenceAdapter implements LoadTransferPort, SaveTransferPort {

	private final TransferRepository repository;
	private final com.bank.transfer_service.transfer.infrastructure.mapper.TransferMapper mapper;

	public TransferPersistenceAdapter(TransferRepository repository, com.bank.transfer_service.transfer.infrastructure.mapper.TransferMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Optional<Transfer> findById(TransferId transferId) {
		if (transferId == null) return Optional.empty();
		return repository.findById(transferId.value()).map(mapper::toDomain);
	}

	@Override
	public Transfer save(Transfer transfer) {
		if (transfer == null) return null;
		TransferDocument doc = mapper.toDocument(transfer);
		TransferDocument saved = repository.save(doc);
		return mapper.toDomain(saved);
	}
}
