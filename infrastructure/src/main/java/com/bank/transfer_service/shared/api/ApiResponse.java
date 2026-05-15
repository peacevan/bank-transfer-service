package com.bank.transfer_service.shared.api;

/**
 * Generic wrapper for all REST responses.
 * Provides a consistent envelope: status, data, and optional error message.
 *
 * @param <T> the payload type
 */
public record ApiResponse<T>(boolean success, T data, String message) {
}
