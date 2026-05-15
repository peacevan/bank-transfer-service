package com.bank.transfer_service.account.infrastructure.mapper;

import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.account.infrastructure.persistence.AccountDocument;
import org.springframework.stereotype.Component;
import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;
import com.bank.transfer_service.account.domain.AccountStatus;

import java.math.BigDecimal;

/**
 * Bidirectional mapper between Account (domain) and AccountDocument (persistence).
 * Keeps domain objects free from MongoDB annotations.
 */
public class AccountMapper {

    public AccountMapper() {}

    public AccountDocument toDocument(Account account) {
        if (account == null) return null;
        AccountDocument d = new AccountDocument();
        d.setId(account.getId() != null ? account.getId().value() : null);
        d.setOwnerName(account.getOwnerName());
        d.setOwnerDocument(account.getOwnerDocument());
        Money balance = account.getBalance();
        if (balance != null) {
            d.setBalance(balance.amount());
            d.setCurrency(balance.currency());
        }
        d.setStatus(account.getStatus() != null ? account.getStatus().name() : null);
        return d;
    }

    public Account toDomain(AccountDocument document) {
        if (document == null) return null;
        AccountId id = document.getId() != null ? AccountId.of(document.getId()) : null;
        BigDecimal balance = document.getBalance();
        Money money = balance != null ? Money.of(balance, document.getCurrency()) : null;
        AccountStatus status = document.getStatus() != null ? AccountStatus.valueOf(document.getStatus()) : null;
        return new Account(id, document.getOwnerName(), document.getOwnerDocument(), money, status);
    }
}
