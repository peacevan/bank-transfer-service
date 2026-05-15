package com.bank.transfer_service.account.application.port.out;

import com.bank.transfer_service.account.domain.Account;

/**
 * Outbound port — write-side persistence for Account.
 * Implemented by AccountPersistenceAdapter (infrastructure layer).
 */
public interface SaveAccountPort {

    Account save(Account account);
}
