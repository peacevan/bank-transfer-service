package com.bank.transfer_service.audit.infrastructure.persistence;

import com.bank.transfer_service.account.application.port.in.CreateAccountUseCase;
import com.bank.transfer_service.account.application.port.in.GetBalanceUseCase;
import com.bank.transfer_service.audit.domain.AuditEventType;
import com.bank.transfer_service.audit.domain.AuditLog;
import com.bank.transfer_service.transfer.application.port.in.ExecuteTransferUseCase;
import com.bank.transfer_service.transfer.application.port.in.GetTransferUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
@SuppressWarnings("unused")
class AuditPersistenceAdapterTest {

    @MockitoBean private CreateAccountUseCase createAccountUseCase;
    @MockitoBean private GetBalanceUseCase getBalanceUseCase;
    @MockitoBean private ExecuteTransferUseCase executeTransferUseCase;
    @MockitoBean private GetTransferUseCase getTransferUseCase;

    @Autowired
    private AuditPersistenceAdapter adapter;

    @Autowired
    private AuditLogRepository repository;

    @BeforeEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void save_persistsAuditLogAndReturnsDomainObject() {
        AuditLog log = buildAuditLog(UUID.randomUUID().toString(), AuditEventType.TRANSFER_COMPLETED);

        AuditLog saved = adapter.save(log);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEventType()).isEqualTo(AuditEventType.TRANSFER_COMPLETED);
        assertThat(saved.getCorrelationId()).isNotNull();
        assertThat(saved.getActorId()).isEqualTo("system");
        assertThat(saved.getPayload()).isEqualTo("{\"key\":\"value\"}");
    }

    @Test
    void findByCorrelationId_returnsOnlyMatchingLogs() {
        String correlationId = UUID.randomUUID().toString();
        adapter.save(buildAuditLog(correlationId, AuditEventType.TRANSFER_INITIATED));
        adapter.save(buildAuditLog(correlationId, AuditEventType.TRANSFER_COMPLETED));
        adapter.save(buildAuditLog(UUID.randomUUID().toString(), AuditEventType.ACCOUNT_CREATED));

        List<AuditLog> result = adapter.findByCorrelationId(correlationId);

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(l -> l.getCorrelationId().equals(correlationId));
    }

    @Test
    void findByCorrelationId_returnsEmpty_whenNoMatch() {
        adapter.save(buildAuditLog(UUID.randomUUID().toString(), AuditEventType.ACCOUNT_CREATED));

        List<AuditLog> result = adapter.findByCorrelationId("non-existent-correlation-id");

        assertThat(result).isEmpty();
    }

    @Test
    void save_withNullAuditLog_returnsNull() {
        AuditLog result = adapter.save(null);

        assertThat(result).isNull();
    }

    @Test
    void findAll_returnsAllPersistedLogs() {
        adapter.save(buildAuditLog(UUID.randomUUID().toString(), AuditEventType.TRANSFER_COMPLETED));
        adapter.save(buildAuditLog(UUID.randomUUID().toString(), AuditEventType.ACCOUNT_CREATED));
        adapter.save(buildAuditLog(UUID.randomUUID().toString(), AuditEventType.BALANCE_QUERIED));

        List<AuditLog> result = adapter.findAll(0, 10);

        assertThat(result).hasSize(3);
    }

    @Test
    void findAll_returnsEmpty_whenNothingPersisted() {
        List<AuditLog> result = adapter.findAll(0, 10);

        assertThat(result).isEmpty();
    }

    private AuditLog buildAuditLog(String correlationId, AuditEventType eventType) {
        return new AuditLog(
                UUID.randomUUID().toString(),
                eventType,
                correlationId,
                "system",
                "{\"key\":\"value\"}",
                Instant.now(),
                Instant.now()
        );
    }
}
