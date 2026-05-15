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
}
