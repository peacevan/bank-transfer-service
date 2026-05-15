package com.bank.transfer_service.audit.domain;

/**
 * Classifies the type of auditable event.
 */
public enum AuditEventType {
    ACCOUNT_CREATED,
    ACCOUNT_BLOCKED,
    ACCOUNT_CLOSED,
    TRANSFER_INITIATED,
    TRANSFER_COMPLETED,
    TRANSFER_FAILED,
    TRANSFER_REVERSED,
    BALANCE_QUERIED
}
