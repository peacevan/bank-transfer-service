package com.bank.transfer_service.transfer.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * MongoDB document mapping for the "transfers" collection.
 * Annotated with @Document(collection = "transfers").
 * Never exposed outside the infrastructure layer.
 */
public class TransferDocument {

    private String id;
    private String sourceAccountId;
    private String targetAccountId;
    private BigDecimal amount;
    private String currency;
    private String type;
    private String status;
    private Instant createdAt;
    private Instant processedAt;
    private String failureReason;
}
