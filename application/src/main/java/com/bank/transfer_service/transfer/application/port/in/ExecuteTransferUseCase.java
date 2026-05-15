package com.bank.transfer_service.transfer.application.port.in;

import com.bank.transfer_service.transfer.api.dto.TransferRequest;
import com.bank.transfer_service.transfer.api.dto.TransferResponse;

/**
 * Inbound port — executes a money transfer between two accounts.
 * Implemented by TransferService (application layer).
 * Called by TransferController (adapter layer).
 */
public interface ExecuteTransferUseCase {

    TransferResponse execute(TransferRequest request);
}
