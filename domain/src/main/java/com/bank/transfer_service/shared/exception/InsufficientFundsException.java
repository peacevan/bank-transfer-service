package com.bank.transfer_service.shared.exception;

/**
 * Thrown when a debit exceeds the available account balance.
 * Translates to HTTP 422 Unprocessable Entity.
 */
public class InsufficientFundsException extends BusinessException {
    public InsufficientFundsException(String accountId) {
        super("Insufficient funds for account: " + accountId);
    }
}
