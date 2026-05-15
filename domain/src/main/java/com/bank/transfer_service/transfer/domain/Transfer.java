package com.bank.transfer_service.transfer.domain;

import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;
import com.bank.transfer_service.shared.domain.TransferId;

import java.time.Instant;

/**
 * Transfer aggregate root.
 *
 * Represents the intent and result of moving money between two accounts.
 * Enforces business rules: same-currency validation, amount > 0.
 *
 * Produces a TransferCompletedEvent on successful execution.
 */
public class Transfer {

    private TransferId id;
    private AccountId sourceAccountId;
    private AccountId targetAccountId;
    private Money amount;
    private TransferType type;
    private TransferStatus status;
    private Instant createdAt;
    private Instant processedAt;
    private String failureReason;

    public Transfer(TransferId id,
                    AccountId sourceAccountId,
                    AccountId targetAccountId,
                    Money amount,
                    TransferType type,
                    TransferStatus status,
                    Instant createdAt,
                    Instant processedAt,
                    String failureReason) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.processedAt = processedAt;
        this.failureReason = failureReason;
    }

    public TransferId getId() { return id; }
    public AccountId getSourceAccountId() { return sourceAccountId; }
    public AccountId getTargetAccountId() { return targetAccountId; }
    public Money getAmount() { return amount; }
    public TransferType getType() { return type; }
    public TransferStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getProcessedAt() { return processedAt; }
    public String getFailureReason() { return failureReason; }
}
