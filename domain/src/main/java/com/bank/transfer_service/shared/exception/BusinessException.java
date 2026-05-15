package com.bank.transfer_service.shared.exception;

/**
 * Base class for all domain/business rule violations.
 * Translates to HTTP 422 Unprocessable Entity.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
