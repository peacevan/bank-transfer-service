package com.bank.transfer_service.transfer.api;

import com.bank.transfer_service.transfer.application.port.in.ExecuteTransferUseCase;
import com.bank.transfer_service.transfer.application.port.in.GetTransferUseCase;

/**
 * REST adapter — exposes Transfer endpoints.
 *
 * Endpoints:
 *  POST  /api/v1/transfers        → ExecuteTransferUseCase
 *  GET   /api/v1/transfers/{id}   → GetTransferUseCase
 *
 * Depends only on inbound ports (never on TransferService directly).
 */
public class TransferController {

    private final ExecuteTransferUseCase executeTransferUseCase;
    private final GetTransferUseCase getTransferUseCase;

    public TransferController(ExecuteTransferUseCase executeTransferUseCase,
                              GetTransferUseCase getTransferUseCase) {
        this.executeTransferUseCase = executeTransferUseCase;
        this.getTransferUseCase = getTransferUseCase;
    }
}
