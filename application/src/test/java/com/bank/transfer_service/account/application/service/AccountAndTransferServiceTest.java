package com.bank.transfer_service.account.application.service;

import com.bank.transfer_service.account.application.port.out.LoadAccountPort;
import com.bank.transfer_service.account.application.port.out.SaveAccountPort;
import com.bank.transfer_service.account.api.dto.CreateAccountRequest;
import com.bank.transfer_service.account.api.dto.AccountResponse;
import com.bank.transfer_service.account.api.dto.BalanceResponse;
import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.account.domain.AccountStatus;
import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;
import com.bank.transfer_service.transfer.application.service.TransferService;
import com.bank.transfer_service.transfer.api.dto.TransferRequest;
import com.bank.transfer_service.transfer.application.port.out.LoadTransferPort;
import com.bank.transfer_service.transfer.application.port.out.SaveTransferPort;
import com.bank.transfer_service.transfer.application.port.out.PublishTransferEventPort;
import com.bank.transfer_service.transfer.domain.Transfer;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AccountAndTransferServiceTest {

    @Test
    public void createAccount_shouldReturnAccountResponse_withZeroBalance() {
        InMemoryAccountStore store = new InMemoryAccountStore();

        AccountService svc = new AccountService(store, store);

        CreateAccountRequest req = new CreateAccountRequest("Alice", "12345678900", "BRL");
        AccountResponse resp = svc.execute(req);

        assertNotNull(resp.accountId());
        assertEquals("Alice", resp.ownerName());
        assertEquals(0, resp.balance().compareTo(BigDecimal.ZERO));
        assertEquals("BRL", resp.currency());
    }

    @Test
    public void getBalance_shouldReturnPersistedBalance() {
        InMemoryAccountStore store = new InMemoryAccountStore();

        AccountId id = AccountId.of(UUID.randomUUID().toString());
        Account account = new Account(id, "Bob", "98765432100", Money.of(new BigDecimal("150.00"), "BRL"), AccountStatus.ACTIVE);
        store.save(account);

        AccountService svc = new AccountService(store, store);

        BalanceResponse balance = svc.execute(id.value());

        assertEquals(0, balance.balance().compareTo(new BigDecimal("150.00")));
        assertEquals("BRL", balance.currency());
    }

    @Test
    public void transfer_shouldMoveFunds_betweenAccounts() {
        InMemoryAccountStore store = new InMemoryAccountStore();
        InMemoryTransferStore transfers = new InMemoryTransferStore();

        AccountId src = AccountId.of(UUID.randomUUID().toString());
        AccountId dst = AccountId.of(UUID.randomUUID().toString());

        Account source = new Account(src, "Source", "11111111111", Money.of(new BigDecimal("1000.00"), "BRL"), AccountStatus.ACTIVE);
        Account target = new Account(dst, "Target", "22222222222", Money.of(new BigDecimal("100.00"), "BRL"), AccountStatus.ACTIVE);
        store.save(source);
        store.save(target);

        TransferService svc = new TransferService(store, store, transfers, new NoOpPublisher(), transfers);

        TransferRequest req = new TransferRequest(src.value(), dst.value(), new BigDecimal("200.00"), "BRL", null, "payment");
        var resp = svc.execute(req);

        // verify balances updated
        Account updatedSource = store.findById(src).get();
        Account updatedTarget = store.findById(dst).get();

        assertEquals(0, updatedSource.getBalance().amount().compareTo(new BigDecimal("800.00")));
        assertEquals(0, updatedTarget.getBalance().amount().compareTo(new BigDecimal("300.00")));

        // verify transfer was saved
        assertNotNull(resp);
        assertNotNull(resp.transferId());
    }

    // --- in-memory test doubles ---
    static class InMemoryAccountStore implements LoadAccountPort, SaveAccountPort {
        private final Map<String, Account> map = new HashMap<>();

        @Override
        public Optional<Account> findById(AccountId id) {
            return Optional.ofNullable(map.get(id.value()));
        }

        @Override
        public Account save(Account account) {
            map.put(account.getId().value(), account);
            return account;
        }
    }

    static class InMemoryTransferStore implements SaveTransferPort, LoadTransferPort {
        private final Map<String, Transfer> map = new HashMap<>();
        @Override
        public Transfer save(Transfer transfer) {
            String id = UUID.randomUUID().toString();
            Transfer t = new Transfer(com.bank.transfer_service.shared.domain.TransferId.of(id), transfer.getSourceAccountId(), transfer.getTargetAccountId(), transfer.getAmount(), transfer.getType(), transfer.getStatus(), transfer.getCreatedAt() != null ? transfer.getCreatedAt() : Instant.now(), transfer.getProcessedAt() != null ? transfer.getProcessedAt() : Instant.now(), transfer.getFailureReason());
            map.put(id, t);
            return t;
        }

        @Override
        public Optional<Transfer> findById(com.bank.transfer_service.shared.domain.TransferId id) {
            return Optional.ofNullable(map.get(id.value()));
        }
    }

    static class NoOpPublisher implements PublishTransferEventPort {
        @Override
        public void publish(com.bank.transfer_service.transfer.domain.TransferCompletedEvent event) {
            // no-op
        }
    }
}
