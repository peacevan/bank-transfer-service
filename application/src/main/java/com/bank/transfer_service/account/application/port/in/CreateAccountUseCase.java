package com.bank.transfer_service.account.application.port.in;

import com.bank.transfer_service.account.api.dto.CreateAccountRequest;
import com.bank.transfer_service.account.api.dto.AccountResponse;

/**
 * Inbound port — creates a new bank account.
 * Implemented by AccountService (application layer).
 * Called by AccountController (adapter layer).
 */
public interface CreateAccountUseCase {

    AccountResponse execute(CreateAccountRequest request);
}
