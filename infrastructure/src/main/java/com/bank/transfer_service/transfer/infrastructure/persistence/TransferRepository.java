package com.bank.transfer_service.transfer.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for TransferDocument.
 * Extended by TransferPersistenceAdapter — not used directly by application layer.
 */
public interface TransferRepository extends MongoRepository<TransferDocument, String> {

    List<TransferDocument> findBySourceAccountId(String accountId);

    List<TransferDocument> findByTargetAccountId(String accountId);
}
