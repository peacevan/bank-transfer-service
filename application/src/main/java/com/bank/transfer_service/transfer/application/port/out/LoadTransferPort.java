package com.bank.transfer_service.transfer.application.port.out;

import com.bank.transfer_service.transfer.domain.Transfer;
import com.bank.transfer_service.shared.domain.TransferId;

import java.util.Optional;

/**
 * Outbound port — read-side persistence for Transfer.
 * Implemented by TransferPersistenceAdapter (infrastructure layer).
 */
public interface LoadTransferPort {

    Optional<Transfer> findById(TransferId transferId);
}
