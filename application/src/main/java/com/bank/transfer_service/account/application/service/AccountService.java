package com.bank.transfer_service.account.application.service;

import com.bank.transfer_service.account.application.port.in.CreateAccountUseCase;
import com.bank.transfer_service.account.application.port.in.GetBalanceUseCase;
import com.bank.transfer_service.account.application.port.out.LoadAccountPort;
import com.bank.transfer_service.account.application.port.out.SaveAccountPort;
import com.bank.transfer_service.account.api.dto.AccountResponse;
import com.bank.transfer_service.account.api.dto.CreateAccountRequest;
import com.bank.transfer_service.account.api.dto.BalanceResponse;
import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.account.domain.AccountStatus;
import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Application service implementing all account use cases.
 */
public class AccountService implements CreateAccountUseCase, GetBalanceUseCase {

	private final LoadAccountPort loadAccountPort;
	private final SaveAccountPort saveAccountPort;

	public AccountService(LoadAccountPort loadAccountPort, SaveAccountPort saveAccountPort) {
		this.loadAccountPort = loadAccountPort;
		this.saveAccountPort = saveAccountPort;
	}

	@Override
	public AccountResponse execute(CreateAccountRequest request) {
		AccountId id = AccountId.of(UUID.randomUUID().toString());
		Money zero = Money.of(BigDecimal.ZERO, request.currency());
		Account account = new Account(id, request.ownerName(), request.ownerDocument(), zero, AccountStatus.ACTIVE);
		Account saved = saveAccountPort.save(account);
		return new AccountResponse(
				saved.getId().value(),
				saved.getOwnerName(),
				saved.getBalance().amount(),
				saved.getBalance().currency(),
				saved.getStatus() != null ? saved.getStatus().name() : null
		);
	}

	@Override
	public BalanceResponse execute(String accountId) {
		AccountId id = AccountId.of(accountId);
		Account account = loadAccountPort.findById(id).orElseThrow(() -> new IllegalArgumentException("account not found"));
		return new BalanceResponse(account.getId().value(), account.getBalance().amount(), account.getBalance().currency());
	}
}
