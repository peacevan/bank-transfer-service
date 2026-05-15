package com.bank.transfer_service.transfer.domain;

/**
 * Processing lifecycle of a Transfer.
 */
public enum TransferStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED,
    REVERSED
}
