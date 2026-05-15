package com.bank.transfer_service.shared.exception;

/**
 * Thrown when a Transfer cannot be found by a given identifier.
 * Translates to HTTP 404 Not Found.
 */
public class TransferNotFoundException extends BusinessException {
    public TransferNotFoundException(String transferId) {
        super("Transfer not found: " + transferId);
    }
}
