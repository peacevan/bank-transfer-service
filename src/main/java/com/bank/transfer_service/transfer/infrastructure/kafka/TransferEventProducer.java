package com.bank.transfer_service.transfer.infrastructure.kafka;

/**
 * Low-level Kafka producer wrapper.
 * Used exclusively by TransferEventPublisherAdapter.
 *
 * Wraps KafkaTemplate to decouple message-sending from port logic.
 * Handles retries, serialisation, and topic routing.
 */
public class TransferEventProducer {
}
