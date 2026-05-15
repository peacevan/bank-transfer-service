package com.bank.transfer_service.account.infrastructure.persistence;

import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.account.application.port.out.LoadAccountPort;
import com.bank.transfer_service.account.application.port.out.SaveAccountPort;
import com.bank.transfer_service.shared.domain.AccountId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountPersistenceAdapter implements LoadAccountPort, SaveAccountPort {

	private final AccountRepository repository;
	private final com.bank.transfer_service.account.infrastructure.mapper.AccountMapper mapper;

	public AccountPersistenceAdapter(AccountRepository repository, com.bank.transfer_service.account.infrastructure.mapper.AccountMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Optional<Account> findById(AccountId accountId) {
		if (accountId == null) return Optional.empty();
		return repository.findById(accountId.value()).map(mapper::toDomain);
	}

	@Override
	public Account save(Account account) {
		if (account == null) return null;
		AccountDocument doc = mapper.toDocument(account);
		AccountDocument saved = repository.save(doc);
		return mapper.toDomain(saved);
	}
}
