package com.bank.transfer_service.transfer.infrastructure.persistence;

import com.bank.transfer_service.transfer.domain.Transfer;
import com.bank.transfer_service.transfer.application.port.out.LoadTransferPort;
import com.bank.transfer_service.transfer.application.port.out.SaveTransferPort;

/**
 * MongoDB adapter implementing transfer persistence ports.
 *
 * Bridges domain Transfer ↔ TransferDocument using TransferMapper.
 * Implements: LoadTransferPort, SaveTransferPort.
 */
public class TransferPersistenceAdapter implements LoadTransferPort, SaveTransferPort {
}
