package com.bank.transfer_service.transfer.infrastructure.persistence;

import com.bank.transfer_service.account.application.port.in.CreateAccountUseCase;
import com.bank.transfer_service.account.application.port.in.GetBalanceUseCase;
import com.bank.transfer_service.shared.domain.AccountId;
import com.bank.transfer_service.shared.domain.Money;
import com.bank.transfer_service.shared.domain.TransferId;
import com.bank.transfer_service.transfer.application.port.in.ExecuteTransferUseCase;
import com.bank.transfer_service.transfer.application.port.in.GetTransferUseCase;
import com.bank.transfer_service.transfer.domain.Transfer;
import com.bank.transfer_service.transfer.domain.TransferStatus;
import com.bank.transfer_service.transfer.domain.TransferType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
@SuppressWarnings("unused")
class TransferPersistenceAdapterTest {

    @MockitoBean private CreateAccountUseCase createAccountUseCase;
    @MockitoBean private GetBalanceUseCase getBalanceUseCase;
    @MockitoBean private ExecuteTransferUseCase executeTransferUseCase;
    @MockitoBean private GetTransferUseCase getTransferUseCase;

    @Autowired
    private TransferPersistenceAdapter adapter;

    @Autowired
    private TransferRepository repository;

    @BeforeEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void save_persistsTransferAndReturnsDomainObject() {
        Transfer transfer = buildTransfer(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        Transfer saved = adapter.save(transfer);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getAmount().amount()).isEqualByComparingTo("250.00");
        assertThat(saved.getAmount().currency()).isEqualTo("BRL");
        assertThat(saved.getStatus()).isEqualTo(TransferStatus.COMPLETED);
        assertThat(saved.getType()).isEqualTo(TransferType.PIX);
    }

    @Test
    void findById_returnsPersistedTransfer() {
        Transfer saved = adapter.save(buildTransfer(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        Optional<Transfer> found = adapter.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getSourceAccountId()).isEqualTo(saved.getSourceAccountId());
        assertThat(found.get().getTargetAccountId()).isEqualTo(saved.getTargetAccountId());
    }

    @Test
    void findById_returnsEmpty_whenIdDoesNotExist() {
        Optional<Transfer> found = adapter.findById(TransferId.of(UUID.randomUUID().toString()));

        assertThat(found).isEmpty();
    }

    @Test
    void findById_returnsEmpty_whenIdIsNull() {
        Optional<Transfer> found = adapter.findById(null);

        assertThat(found).isEmpty();
    }

    @Test
    void save_withNullTransfer_returnsNull() {
        Transfer result = adapter.save(null);

        assertThat(result).isNull();
    }

    @Test
    void repository_findBySourceAccountId_returnsOnlyMatchingTransfers() {
        String sourceId = UUID.randomUUID().toString();
        adapter.save(buildTransfer(sourceId, UUID.randomUUID().toString()));
        adapter.save(buildTransfer(sourceId, UUID.randomUUID().toString()));
        adapter.save(buildTransfer(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        List<TransferDocument> result = repository.findBySourceAccountId(sourceId);

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(doc -> doc.getSourceAccountId().equals(sourceId));
    }

    @Test
    void repository_findByTargetAccountId_returnsOnlyMatchingTransfers() {
        String targetId = UUID.randomUUID().toString();
        adapter.save(buildTransfer(UUID.randomUUID().toString(), targetId));
        adapter.save(buildTransfer(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        List<TransferDocument> result = repository.findByTargetAccountId(targetId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTargetAccountId()).isEqualTo(targetId);
    }

    private Transfer buildTransfer(String sourceId, String targetId) {
        return new Transfer(
                TransferId.of(UUID.randomUUID().toString()),
                AccountId.of(sourceId),
                AccountId.of(targetId),
                Money.of(new BigDecimal("250.00"), "BRL"),
                TransferType.PIX,
                TransferStatus.COMPLETED,
                Instant.now(),
                Instant.now(),
                null
        );
    }
}
