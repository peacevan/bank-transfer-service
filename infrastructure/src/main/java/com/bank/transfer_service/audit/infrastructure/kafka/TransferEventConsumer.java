package com.bank.transfer_service.audit.infrastructure.kafka;

import com.bank.transfer_service.transfer.domain.TransferCompletedEvent;

/**
 * Kafka consumer — listens on the "transfer-completed" topic.
 *
 * On each message:
 *  1. Deserialises the payload into a TransferCompletedEvent.
 *  2. Maps it to an AuditLog domain object.
 *  3. Persists it via SaveAuditLogPort.
 *
 * Annotated with @KafkaListener(topics = "transfer-completed", groupId = "audit-group").
 */
public class TransferEventConsumer {
}
