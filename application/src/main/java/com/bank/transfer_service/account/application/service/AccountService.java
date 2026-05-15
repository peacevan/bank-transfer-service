package com.bank.transfer_service.account.application.service;

import com.bank.transfer_service.account.application.port.in.CreateAccountUseCase;
import com.bank.transfer_service.account.application.port.in.GetBalanceUseCase;
import com.bank.transfer_service.account.api.dto.AccountResponse;
import com.bank.transfer_service.account.api.dto.CreateAccountRequest;
import com.bank.transfer_service.account.api.dto.BalanceResponse;

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

	@Override
	public AccountResponse execute(CreateAccountRequest request) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public BalanceResponse execute(String accountId) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
