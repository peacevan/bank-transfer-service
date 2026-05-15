package com.bank.transfer_service.transfer.infrastructure.mapper;

import com.bank.transfer_service.transfer.domain.Transfer;
import com.bank.transfer_service.transfer.infrastructure.persistence.TransferDocument;

/**
 * Bidirectional mapper between Transfer (domain) and TransferDocument (persistence).
 * Keeps domain objects free from MongoDB annotations.
 */
public class TransferMapper {

    public TransferDocument toDocument(Transfer transfer) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Transfer toDomain(TransferDocument document) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
