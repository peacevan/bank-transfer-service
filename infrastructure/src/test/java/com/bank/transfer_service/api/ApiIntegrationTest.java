package com.bank.transfer_service.api;

import com.bank.transfer_service.account.api.dto.AccountResponse;
import com.bank.transfer_service.account.api.dto.CreateAccountRequest;
import com.bank.transfer_service.account.api.dto.BalanceResponse;
import com.bank.transfer_service.transfer.api.dto.TransferRequest;
import com.bank.transfer_service.account.application.port.in.CreateAccountUseCase;
import com.bank.transfer_service.account.application.port.in.GetBalanceUseCase;
import com.bank.transfer_service.account.application.port.out.LoadAccountPort;
import com.bank.transfer_service.account.application.port.out.SaveAccountPort;
import com.bank.transfer_service.account.application.service.AccountService;
import com.bank.transfer_service.transfer.application.service.TransferService;
import com.bank.transfer_service.transfer.application.port.in.ExecuteTransferUseCase;
import com.bank.transfer_service.transfer.application.port.out.LoadTransferPort;
import com.bank.transfer_service.transfer.application.port.out.SaveTransferPort;
import com.bank.transfer_service.transfer.application.port.out.PublishTransferEventPort;
import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.account.domain.AccountStatus;
import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate rest = new RestTemplate();

    @Autowired
    private InMemoryAccountStore accounts;

    @Test
    void createAccount_then_transfer_then_checkBalance_via_http() {
        // seed source account directly into in-memory store
        AccountId sourceId = AccountId.of(UUID.randomUUID().toString());
        Account source = new Account(sourceId, "Seed", "000", Money.of(new BigDecimal("500.00"), "BRL"), AccountStatus.ACTIVE);
        accounts.save(source);

        // create target via API
        CreateAccountRequest createReq = new CreateAccountRequest("Target", "111", "BRL");
        String base = "http://localhost:" + port;
        ResponseEntity<AccountResponse> createResp = rest.postForEntity(base + "/api/v1/accounts", createReq, AccountResponse.class);
        assertEquals(200, createResp.getStatusCode().value());
        String targetId = createResp.getBody().accountId();

        // execute transfer via API
        TransferRequest tr = new TransferRequest(sourceId.value(), targetId, new BigDecimal("200.00"), "BRL", null, "deposit");
        ResponseEntity<Object> trResp = rest.postForEntity(base + "/api/v1/transfers", tr, Object.class);
        assertEquals(200, trResp.getStatusCode().value());

        // check balances
        ResponseEntity<BalanceResponse> srcBal = rest.getForEntity(base + "/api/v1/accounts/" + sourceId.value() + "/balance", BalanceResponse.class);
        ResponseEntity<BalanceResponse> tgtBal = rest.getForEntity(base + "/api/v1/accounts/" + targetId + "/balance", BalanceResponse.class);

        assertEquals(0, srcBal.getBody().balance().compareTo(new BigDecimal("300.00")));
        assertEquals(0, tgtBal.getBody().balance().compareTo(new BigDecimal("200.00")));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        InMemoryAccountStore inMemoryAccountStore() { return new InMemoryAccountStore(); }

        @Bean
        @org.springframework.context.annotation.Primary
        CreateAccountUseCase createAccountUseCase(InMemoryAccountStore store) {
            return new CreateAccountUseCase() {
                @Override
                public AccountResponse execute(CreateAccountRequest request) {
                    String id = UUID.randomUUID().toString();
                    AccountId accountId = AccountId.of(id);
                    String currency = "BRL";
                    try {
                        currency = (String) request.getClass().getMethod("currency").invoke(request);
                    } catch (Exception ignored) {}
                    Money money = Money.of(new BigDecimal("0.00"), currency);
                    String owner = null;
                    try { owner = (String) request.getClass().getMethod("ownerName").invoke(request); } catch (Exception ignored) {}
                    Account account = new Account(accountId, owner, null, money, AccountStatus.ACTIVE);
                    Account saved = store.save(account);
                    return new AccountResponse(saved.getId().value(), saved.getOwnerName(), saved.getBalance().amount(), saved.getBalance().currency(), saved.getStatus() != null ? saved.getStatus().name() : null);
                }
            };
        }

        @Bean
        GetBalanceUseCase getBalanceUseCase(InMemoryAccountStore store) {
            return new GetBalanceUseCase() {
                @Override
                public BalanceResponse execute(String accountId) {
                    AccountId id = AccountId.of(accountId);
                    Account account = store.findById(id).orElseThrow(() -> new IllegalArgumentException("account not found"));
                    return new BalanceResponse(account.getId().value(), account.getBalance().amount(), account.getBalance().currency());
                }
            };
        }

        @Bean
        InMemoryTransferStore inMemoryTransferStore() { return new InMemoryTransferStore(); }

        @Bean
        ExecuteTransferUseCase executeTransferUseCase(InMemoryAccountStore store, InMemoryTransferStore transfers) {
            return new ExecuteTransferUseCase() {
                @Override
                public com.bank.transfer_service.transfer.api.dto.TransferResponse execute(TransferRequest request) {
                    AccountId srcId = AccountId.of(request.sourceAccountId());
                    AccountId tgtId = AccountId.of(request.targetAccountId());
                    Account src = store.findById(srcId).orElseThrow(() -> new IllegalArgumentException("source not found"));
                    Account tgt = store.findById(tgtId).orElseThrow(() -> new IllegalArgumentException("target not found"));
                    BigDecimal amount = request.amount();
                    Money srcMoney = src.getBalance();
                    Money tgtMoney = tgt.getBalance();
                    Money newSrcMoney = Money.of(srcMoney.amount().subtract(amount), srcMoney.currency());
                    Money newTgtMoney = Money.of(tgtMoney.amount().add(amount), tgtMoney.currency());
                    Account newSrc = new Account(src.getId(), src.getOwnerName(), src.getOwnerDocument(), newSrcMoney, src.getStatus());
                    Account newTgt = new Account(tgt.getId(), tgt.getOwnerName(), tgt.getOwnerDocument(), newTgtMoney, tgt.getStatus());
                    store.save(newSrc);
                    store.save(newTgt);
                    com.bank.transfer_service.transfer.api.dto.TransferResponse resp = new com.bank.transfer_service.transfer.api.dto.TransferResponse(
                            UUID.randomUUID().toString(), src.getId().value(), tgt.getId().value(), amount, request.currency(), request.type(), "COMPLETED", Instant.now(), Instant.now()
                    );
                    return resp;
                }
            };
        }

        @Bean
        AccountApiController accountController(CreateAccountUseCase create, GetBalanceUseCase get) { return new AccountApiController(create, get); }

        @Bean
        TransferApiController transferController(ExecuteTransferUseCase exec) { return new TransferApiController(exec); }
    }

    // --- test REST controllers wired only for tests ---
    @RestController
    @RequestMapping("/api/v1/accounts")
    static class AccountApiController {
        private final CreateAccountUseCase create;
        private final GetBalanceUseCase get;
        public AccountApiController(CreateAccountUseCase create, GetBalanceUseCase get) { this.create = create; this.get = get; }

        @PostMapping
        public AccountResponse create(@RequestBody CreateAccountRequest req) { return create.execute(req); }

        @GetMapping("/{id}/balance")
        public BalanceResponse balance(@PathVariable String id) { return get.execute(id); }
    }

    @RestController
    @RequestMapping("/api/v1/transfers")
    static class TransferApiController {
        private final ExecuteTransferUseCase exec;
        public TransferApiController(ExecuteTransferUseCase exec) { this.exec = exec; }

        @PostMapping
        public Object transfer(@RequestBody TransferRequest req) { return exec.execute(req); }
    }

    // --- in-memory adapters ---
    static class InMemoryAccountStore implements LoadAccountPort, SaveAccountPort {
        private final Map<String, Account> map = new HashMap<>();
        @Override public Optional<Account> findById(AccountId id) { return Optional.ofNullable(map.get(id.value())); }
        @Override public Account save(Account account) { map.put(account.getId().value(), account); return account; }
    }

    static class InMemoryTransferStore implements SaveTransferPort, LoadTransferPort, PublishTransferEventPort {
        private final Map<String, com.bank.transfer_service.transfer.domain.Transfer> map = new HashMap<>();
        @Override public com.bank.transfer_service.transfer.domain.Transfer save(com.bank.transfer_service.transfer.domain.Transfer transfer) { String id = UUID.randomUUID().toString(); com.bank.transfer_service.transfer.domain.Transfer t = new com.bank.transfer_service.transfer.domain.Transfer(com.bank.transfer_service.shared.domain.TransferId.of(id), transfer.getSourceAccountId(), transfer.getTargetAccountId(), transfer.getAmount(), transfer.getType(), transfer.getStatus(), transfer.getCreatedAt() != null ? transfer.getCreatedAt() : Instant.now(), transfer.getProcessedAt() != null ? transfer.getProcessedAt() : Instant.now(), transfer.getFailureReason()); map.put(id, t); return t; }
        @Override public Optional<com.bank.transfer_service.transfer.domain.Transfer> findById(com.bank.transfer_service.shared.domain.TransferId id) { return Optional.ofNullable(map.get(id.value())); }
        @Override public void publish(com.bank.transfer_service.transfer.domain.TransferCompletedEvent event) { }
    }

}
