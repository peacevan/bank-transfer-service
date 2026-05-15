package com.bank.transfer_service.account.application.port.in;

import com.bank.transfer_service.account.api.dto.BalanceResponse;

/**
 * Inbound port — retrieves the current balance of an account.
 * Implemented by AccountService (application layer).
 * Called by AccountController (adapter layer).
 */
public interface GetBalanceUseCase {

    BalanceResponse execute(String accountId);
}
