package com.bank.transfer_service.account.application.port.out;

import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.shared.domain.AccountId;

import java.util.Optional;

/**
 * Outbound port — read-side persistence for Account.
 * Implemented by AccountPersistenceAdapter (infrastructure layer).
 */
public interface LoadAccountPort {

    Optional<Account> findById(AccountId accountId);
}
