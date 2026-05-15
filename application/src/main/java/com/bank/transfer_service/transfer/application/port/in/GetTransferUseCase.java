package com.bank.transfer_service.transfer.application.port.in;

import com.bank.transfer_service.transfer.api.dto.TransferResponse;

/**
 * Inbound port — retrieves a transfer by its identifier.
 * Implemented by TransferService (application layer).
 */
public interface GetTransferUseCase {

    TransferResponse execute(String transferId);
}
