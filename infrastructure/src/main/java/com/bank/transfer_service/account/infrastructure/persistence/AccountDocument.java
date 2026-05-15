package com.bank.transfer_service.account.infrastructure.persistence;

import java.math.BigDecimal;

/**
 * MongoDB document mapping for the "accounts" collection.
 * Annotated with @Document(collection = "accounts").
 * Never exposed outside the infrastructure layer.
 */
public class AccountDocument {

    private String id;
    private String ownerName;
    private String ownerDocument;
    private BigDecimal balance;
    private String currency;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerDocument() {
        return ownerDocument;
    }

    public void setOwnerDocument(String ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
