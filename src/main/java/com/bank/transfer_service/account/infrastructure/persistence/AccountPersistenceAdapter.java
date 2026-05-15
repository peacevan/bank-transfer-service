package com.bank.transfer_service.account.infrastructure.persistence;

import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.account.application.port.out.LoadAccountPort;
import com.bank.transfer_service.account.application.port.out.SaveAccountPort;

/**
 * MongoDB adapter implementing account persistence ports.
 *
 * Bridges domain Account ↔ AccountDocument using AccountMapper.
 * Implements: LoadAccountPort, SaveAccountPort.
 */
public class AccountPersistenceAdapter implements LoadAccountPort, SaveAccountPort {
}
