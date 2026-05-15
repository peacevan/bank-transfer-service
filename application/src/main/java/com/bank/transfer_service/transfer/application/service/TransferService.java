package com.bank.transfer_service.transfer.application.service;

import com.bank.transfer_service.transfer.application.port.in.ExecuteTransferUseCase;
import com.bank.transfer_service.transfer.application.port.in.GetTransferUseCase;
import com.bank.transfer_service.transfer.application.port.out.LoadTransferPort;
import com.bank.transfer_service.transfer.application.port.out.SaveTransferPort;
import com.bank.transfer_service.transfer.application.port.out.PublishTransferEventPort;
import com.bank.transfer_service.transfer.api.dto.TransferRequest;
import com.bank.transfer_service.transfer.api.dto.TransferResponse;
import com.bank.transfer_service.account.application.port.out.LoadAccountPort;
import com.bank.transfer_service.account.application.port.out.SaveAccountPort;
import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;
import com.bank.transfer_service.shared.domain.TransferId;
import com.bank.transfer_service.transfer.domain.Transfer;
import com.bank.transfer_service.transfer.domain.TransferCompletedEvent;
import com.bank.transfer_service.transfer.domain.TransferType;
import com.bank.transfer_service.transfer.domain.TransferStatus;
import com.bank.transfer_service.shared.exception.TransferNotFoundException;

import java.time.Instant;
import java.util.UUID;

/**
 * Application service implementing all transfer use cases.
 */
public class TransferService implements ExecuteTransferUseCase, GetTransferUseCase {

	private final LoadAccountPort loadAccountPort;
	private final SaveAccountPort saveAccountPort;
	private final SaveTransferPort saveTransferPort;
	private final PublishTransferEventPort publishTransferEventPort;
	private final LoadTransferPort loadTransferPort;

	public TransferService(LoadAccountPort loadAccountPort,
						   SaveAccountPort saveAccountPort,
						   SaveTransferPort saveTransferPort,
						   PublishTransferEventPort publishTransferEventPort,
						   LoadTransferPort loadTransferPort) {
		this.loadAccountPort = loadAccountPort;
		this.saveAccountPort = saveAccountPort;
		this.saveTransferPort = saveTransferPort;
		this.publishTransferEventPort = publishTransferEventPort;
		this.loadTransferPort = loadTransferPort;
	}

	@Override
	public TransferResponse execute(TransferRequest request) {
		AccountId sourceId = AccountId.of(request.sourceAccountId());
		AccountId targetId = AccountId.of(request.targetAccountId());
		Money amount = Money.of(request.amount(), request.currency());

		Account source = loadAccountPort.findById(sourceId).orElseThrow(() -> new IllegalArgumentException("source account not found"));
		Account target = loadAccountPort.findById(targetId).orElseThrow(() -> new IllegalArgumentException("target account not found"));

		// Balance checks and updates
		if (source.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException("insufficient funds");
		}

		Account updatedSource = new Account(source.getId(), source.getOwnerName(), source.getOwnerDocument(), source.getBalance().minus(amount), source.getStatus());
		Account updatedTarget = new Account(target.getId(), target.getOwnerName(), target.getOwnerDocument(), target.getBalance().plus(amount), target.getStatus());

		saveAccountPort.save(updatedSource);
		saveAccountPort.save(updatedTarget);

		Transfer transfer = new Transfer(null, sourceId, targetId, amount,
				request.type() != null ? TransferType.valueOf(request.type()) : TransferType.INTERNAL,
				TransferStatus.COMPLETED,
				Instant.now(), Instant.now(), null);

		Transfer saved = saveTransferPort.save(transfer);

		// publish domain event
		publishTransferEventPort.publish(new TransferCompletedEvent(
				saved.getId(),
				saved.getSourceAccountId().value(),
				saved.getTargetAccountId().value(),
				saved.getAmount().amount(),
				saved.getAmount().currency(),
				Instant.now()
		));

		return new TransferResponse(
				saved.getId() != null ? saved.getId().value() : null,
				saved.getSourceAccountId() != null ? saved.getSourceAccountId().value() : null,
				saved.getTargetAccountId() != null ? saved.getTargetAccountId().value() : null,
				saved.getAmount() != null ? saved.getAmount().amount() : null,
				saved.getAmount() != null ? saved.getAmount().currency() : null,
				saved.getType() != null ? saved.getType().name() : null,
				saved.getStatus() != null ? saved.getStatus().name() : null,
				saved.getCreatedAt(),
				saved.getProcessedAt()
		);
	}

	@Override
	public TransferResponse execute(String transferId) {
		TransferId id = TransferId.of(transferId);
		Transfer transfer = loadTransferPort.findById(id).orElseThrow(() -> new TransferNotFoundException(transferId));
		return new TransferResponse(
				transfer.getId() != null ? transfer.getId().value() : null,
				transfer.getSourceAccountId() != null ? transfer.getSourceAccountId().value() : null,
				transfer.getTargetAccountId() != null ? transfer.getTargetAccountId().value() : null,
				transfer.getAmount() != null ? transfer.getAmount().amount() : null,
				transfer.getAmount() != null ? transfer.getAmount().currency() : null,
				transfer.getType() != null ? transfer.getType().name() : null,
				transfer.getStatus() != null ? transfer.getStatus().name() : null,
				transfer.getCreatedAt(),
				transfer.getProcessedAt()
		);
	}
}
