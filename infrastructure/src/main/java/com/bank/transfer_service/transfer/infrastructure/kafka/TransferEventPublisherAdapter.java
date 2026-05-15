package com.bank.transfer_service.transfer.infrastructure.kafka;

import com.bank.transfer_service.transfer.application.port.out.PublishTransferEventPort;
import com.bank.transfer_service.transfer.domain.TransferCompletedEvent;

/**
 * Kafka adapter — implements PublishTransferEventPort.
 *
 * Serialises TransferCompletedEvent to JSON and sends it to the
 * "transfer-completed" topic using KafkaTemplate.
 */
public class TransferEventPublisherAdapter implements PublishTransferEventPort {

    @Override
    public void publish(TransferCompletedEvent event) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
