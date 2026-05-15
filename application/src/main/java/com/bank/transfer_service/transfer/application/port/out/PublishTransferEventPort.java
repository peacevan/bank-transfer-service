package com.bank.transfer_service.transfer.application.port.out;

import com.bank.transfer_service.transfer.domain.TransferCompletedEvent;

/**
 * Outbound port — publishes transfer domain events.
 * Implemented by TransferEventPublisherAdapter (infrastructure/kafka).
 */
public interface PublishTransferEventPort {

    void publish(TransferCompletedEvent event);
}
