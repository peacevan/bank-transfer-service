package com.bank.transfer_service.account.infrastructure.persistence;

import com.bank.transfer_service.account.application.port.in.CreateAccountUseCase;
import com.bank.transfer_service.account.application.port.in.GetBalanceUseCase;
import com.bank.transfer_service.account.domain.Account;
import com.bank.transfer_service.account.domain.AccountStatus;
import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;
import com.bank.transfer_service.transfer.application.port.in.ExecuteTransferUseCase;
import com.bank.transfer_service.transfer.application.port.in.GetTransferUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration",
                "de.flapdoodle.mongodb.embedded.version=7.0.0"
        })
@SuppressWarnings("unused")
class AccountPersistenceAdapterTest {

    @MockitoBean private CreateAccountUseCase createAccountUseCase;
    @MockitoBean private GetBalanceUseCase getBalanceUseCase;
    @MockitoBean private ExecuteTransferUseCase executeTransferUseCase;
    @MockitoBean private GetTransferUseCase getTransferUseCase;

    @Autowired
    private AccountPersistenceAdapter adapter;

    @Autowired
    private AccountRepository repository;

    @BeforeEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void save_persistsAccountAndReturnsDomainObject() {
        Account account = buildAccount();

        Account saved = adapter.save(account);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getOwnerName()).isEqualTo("João Silva");
        assertThat(saved.getOwnerDocument()).isEqualTo("123.456.789-00");
        assertThat(saved.getBalance().amount()).isEqualByComparingTo("1000.00");
        assertThat(saved.getBalance().currency()).isEqualTo("BRL");
        assertThat(saved.getStatus()).isEqualTo(AccountStatus.ACTIVE);
    }

    @Test
    void findById_returnsPersistedAccount() {
        Account saved = adapter.save(buildAccount());

        Optional<Account> found = adapter.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getOwnerName()).isEqualTo("João Silva");
        assertThat(found.get().getBalance().amount()).isEqualByComparingTo("1000.00");
    }

    @Test
    void findById_returnsEmpty_whenIdDoesNotExist() {
        Optional<Account> found = adapter.findById(AccountId.of(UUID.randomUUID().toString()));

        assertThat(found).isEmpty();
    }

    @Test
    void findById_returnsEmpty_whenIdIsNull() {
        Optional<Account> found = adapter.findById(null);

        assertThat(found).isEmpty();
    }

    @Test
    void save_withNullAccount_returnsNull() {
        Account result = adapter.save(null);

        assertThat(result).isNull();
    }

    @Test
    void save_updatesExistingAccount_withoutCreatingDuplicate() {
        Account original = adapter.save(buildAccount());
        Account updated = new Account(
                original.getId(),
                original.getOwnerName(),
                original.getOwnerDocument(),
                Money.of(new BigDecimal("500.00"), "BRL"),
                AccountStatus.ACTIVE
        );

        Account result = adapter.save(updated);

        assertThat(result.getBalance().amount()).isEqualByComparingTo("500.00");
        assertThat(repository.count()).isEqualTo(1);
    }

    private Account buildAccount() {
        return new Account(
                AccountId.of(UUID.randomUUID().toString()),
                "João Silva",
                "123.456.789-00",
                Money.of(new BigDecimal("1000.00"), "BRL"),
                AccountStatus.ACTIVE
        );
    }
}
