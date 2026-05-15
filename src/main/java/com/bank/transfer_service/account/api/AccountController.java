package com.bank.transfer_service.account.api;

import com.bank.transfer_service.account.application.port.in.CreateAccountUseCase;
import com.bank.transfer_service.account.application.port.in.GetBalanceUseCase;

/**
 * REST adapter — exposes Account endpoints.
 *
 * Endpoints:
 *  POST   /api/v1/accounts          → CreateAccountUseCase
 *  GET    /api/v1/accounts/{id}/balance → GetBalanceUseCase
 *
 * Depends only on inbound ports (never on AccountService directly).
 */
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetBalanceUseCase getBalanceUseCase;

    public AccountController(CreateAccountUseCase createAccountUseCase,
                             GetBalanceUseCase getBalanceUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.getBalanceUseCase = getBalanceUseCase;
    }
}
