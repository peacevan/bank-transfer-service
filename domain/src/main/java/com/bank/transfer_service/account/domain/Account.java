package com.bank.transfer_service.account.domain;

import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;

/**
 * Account aggregate root.
 *
 * Responsibilities:
 *  - enforce business invariants (non-negative balance)
 *  - expose domain operations: credit, debit
 *
 * Persisted via AccountDocument (infrastructure layer).
 */
public class Account {

    private AccountId id;
    private String ownerName;
    private String ownerDocument;   // CPF / CNPJ
    private Money balance;
    private AccountStatus status;
    public Account(AccountId id, String ownerName, String ownerDocument, Money balance, AccountStatus status) {
        this.id = id;
        this.ownerName = ownerName;
        this.ownerDocument = ownerDocument;
        this.balance = balance;
        this.status = status;
    }

    public AccountId getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerDocument() {
        return ownerDocument;
    }

    public Money getBalance() {
        return balance;
    }

    public AccountStatus getStatus() {
        return status;
    }
}
