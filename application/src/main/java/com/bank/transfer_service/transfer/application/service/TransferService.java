package com.bank.transfer_service.transfer.application.service;

import com.bank.transfer_service.transfer.application.port.in.ExecuteTransferUseCase;
import com.bank.transfer_service.transfer.application.port.in.GetTransferUseCase;
import com.bank.transfer_service.transfer.api.dto.TransferRequest;
import com.bank.transfer_service.transfer.api.dto.TransferResponse;

/**
 * Application service implementing all transfer use cases.
 *
 * Orchestrates:
 *  - Account balance checks (LoadAccountPort / SaveAccountPort)
 *  - Transfer persistence (SaveTransferPort)
 *  - Event publishing (PublishTransferEventPort)
 *
 * Annotated with @Transactional for atomicity.
 * Must NOT depend on Spring Web or infrastructure classes.
 */
public class TransferService implements ExecuteTransferUseCase, GetTransferUseCase {

	@Override
	public TransferResponse execute(TransferRequest request) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public TransferResponse execute(String transferId) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
