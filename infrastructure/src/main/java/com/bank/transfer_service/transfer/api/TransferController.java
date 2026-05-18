package com.bank.transfer_service.transfer.api;

import com.bank.transfer_service.transfer.api.dto.TransferRequest;
import com.bank.transfer_service.transfer.api.dto.TransferResponse;
import com.bank.transfer_service.transfer.application.port.in.ExecuteTransferUseCase;
import com.bank.transfer_service.transfer.application.port.in.GetTransferUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final ExecuteTransferUseCase executeTransferUseCase;
    private final GetTransferUseCase getTransferUseCase;

    public TransferController(ExecuteTransferUseCase executeTransferUseCase,
                              GetTransferUseCase getTransferUseCase) {
        this.executeTransferUseCase = executeTransferUseCase;
        this.getTransferUseCase = getTransferUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferResponse executeTransfer(@RequestBody TransferRequest request) {
        return executeTransferUseCase.execute(request);
    }

    @GetMapping("/{id}")
    public TransferResponse getTransfer(@PathVariable String id) {
        return getTransferUseCase.execute(id);
    }
}
