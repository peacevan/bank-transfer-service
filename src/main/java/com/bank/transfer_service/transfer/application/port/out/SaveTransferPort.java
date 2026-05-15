package com.bank.transfer_service.transfer.application.port.out;

import com.bank.transfer_service.transfer.domain.Transfer;

/**
 * Outbound port — write-side persistence for Transfer.
 * Implemented by TransferPersistenceAdapter (infrastructure layer).
 */
public interface SaveTransferPort {

    Transfer save(Transfer transfer);
}
