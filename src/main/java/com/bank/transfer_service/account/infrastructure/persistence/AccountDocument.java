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
}
