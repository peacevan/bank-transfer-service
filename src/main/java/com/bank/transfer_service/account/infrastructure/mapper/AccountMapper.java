package com.bank.transfer_service.account.infrastructure.mapper;

import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.account.infrastructure.persistence.AccountDocument;

/**
 * Bidirectional mapper between Account (domain) and AccountDocument (persistence).
 * Keeps domain objects free from MongoDB annotations.
 */
public class AccountMapper {

    public AccountDocument toDocument(Account account) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Account toDomain(AccountDocument document) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
