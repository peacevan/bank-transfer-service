package com.bank.transfer_service.account.application.service;

import com.bank.transfer_service.account.application.port.in.CreateAccountUseCase;
import com.bank.transfer_service.account.application.port.in.GetBalanceUseCase;

/**
 * Application service implementing all account use cases.
 *
 * Orchestrates:
 *  - domain logic (Account aggregate)
 *  - outbound ports (LoadAccountPort, SaveAccountPort)
 *
 * Must NOT depend on Spring Web or infrastructure classes.
 */
public class AccountService implements CreateAccountUseCase, GetBalanceUseCase {
}
