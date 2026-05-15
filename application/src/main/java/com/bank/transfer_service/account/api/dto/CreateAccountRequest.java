package com.bank.transfer_service.account.api.dto;

public record CreateAccountRequest(
        String ownerName,
        String ownerDocument,
        String currency
) {
}
