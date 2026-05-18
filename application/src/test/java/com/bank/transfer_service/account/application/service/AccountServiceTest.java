package com.bank.transfer_service.account.application.service;

import com.bank.transfer_service.account.api.dto.AccountResponse;
import com.bank.transfer_service.account.api.dto.CreateAccountRequest;
import com.bank.transfer_service.account.api.dto.BalanceResponse;
import com.bank.transfer_service.account.application.port.out.LoadAccountPort;
import com.bank.transfer_service.account.application.port.out.SaveAccountPort;
import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.account.domain.AccountStatus;
import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    @Test
    void createAccount_returnsAccountResponse() {
        SaveAccountPort save = account -> account; // echo back saved account
        LoadAccountPort load = id -> Optional.empty();
        AccountService svc = new AccountService(load, save);

        CreateAccountRequest req = new CreateAccountRequest(
                "Alice",
                "12345678900",
                "BRL"
        );

        AccountResponse res = svc.execute(req);
        assertNotNull(res);
        assertNotNull(res.accountId());
        assertEquals("Alice", res.ownerName());
        assertEquals(0, res.balance().compareTo(BigDecimal.ZERO));
        assertEquals("BRL", res.currency());
    }

    @Test
    void getBalance_returnsBalance() {
        String id = UUID.randomUUID().toString();
        AccountId aid = AccountId.of(id);
        Money money = Money.of(new BigDecimal("100.00"), "BRL");
        Account account = new Account(aid, "Bob", "98765432100", money, AccountStatus.ACTIVE);

        LoadAccountPort load = accountId -> Optional.of(account);
        SaveAccountPort save = a -> a;
        AccountService svc = new AccountService(load, save);

        BalanceResponse res = svc.execute(id);
        assertNotNull(res);
        assertEquals(id, res.accountId());
        assertEquals(0, res.balance().compareTo(new BigDecimal("100.00")));
        assertEquals("BRL", res.currency());
    }

    @Test
    void getBalance_notFound_throws() {
        LoadAccountPort load = accountId -> Optional.empty();
        SaveAccountPort save = a -> a;
        AccountService svc = new AccountService(load, save);
        String id = UUID.randomUUID().toString();
        assertThrows(IllegalArgumentException.class, () -> svc.execute(id));
    }
}
