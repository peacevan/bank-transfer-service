package com.bank.transfer_service.transfer.infrastructure.mapper;

import com.bank.transfer_service.transfer.domain.Transfer;
import com.bank.transfer_service.transfer.infrastructure.persistence.TransferDocument;
import org.springframework.stereotype.Component;
import com.bank.transfer_service.shared.domain.TransferId;
import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;
import com.bank.transfer_service.transfer.domain.TransferType;
import com.bank.transfer_service.transfer.domain.TransferStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Bidirectional mapper between Transfer (domain) and TransferDocument (persistence).
 * Keeps domain objects free from MongoDB annotations.
 */
@Component
public class TransferMapper {

    public TransferMapper() {}

    public TransferDocument toDocument(Transfer transfer) {
        if (transfer == null) return null;
        TransferDocument d = new TransferDocument();
        d.setId(transfer.getId() != null ? transfer.getId().value() : null);
        d.setSourceAccountId(transfer.getSourceAccountId() != null ? transfer.getSourceAccountId().value() : null);
        d.setTargetAccountId(transfer.getTargetAccountId() != null ? transfer.getTargetAccountId().value() : null);
        Money m = transfer.getAmount();
        if (m != null) {
            d.setAmount(m.amount());
            d.setCurrency(m.currency());
        }
        d.setType(transfer.getType() != null ? transfer.getType().name() : null);
        d.setStatus(transfer.getStatus() != null ? transfer.getStatus().name() : null);
        d.setCreatedAt(transfer.getCreatedAt());
        d.setProcessedAt(transfer.getProcessedAt());
        d.setFailureReason(transfer.getFailureReason());
        return d;
    }

    public Transfer toDomain(TransferDocument document) {
        if (document == null) return null;
        TransferId id = document.getId() != null ? TransferId.of(document.getId()) : null;
        AccountId source = document.getSourceAccountId() != null ? AccountId.of(document.getSourceAccountId()) : null;
        AccountId target = document.getTargetAccountId() != null ? AccountId.of(document.getTargetAccountId()) : null;
        BigDecimal amount = document.getAmount();
        Money money = amount != null ? Money.of(amount, document.getCurrency()) : null;
        TransferType type = document.getType() != null ? TransferType.valueOf(document.getType()) : null;
        TransferStatus status = document.getStatus() != null ? TransferStatus.valueOf(document.getStatus()) : null;
        Instant created = document.getCreatedAt();
        Instant processed = document.getProcessedAt();
        return new Transfer(id, source, target, money, type, status, created, processed, document.getFailureReason());
    }
}
