package com.bank.transfer_service.shared.exception;

/**
 * Thrown when an Account cannot be found by a given identifier.
 * Translates to HTTP 404 Not Found.
 */
public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(String accountId) {
        super("Account not found: " + accountId);
    }
}
