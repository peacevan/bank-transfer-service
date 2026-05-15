package com.bank.transfer_service.account.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for AccountDocument.
 * Extended by AccountPersistenceAdapter — not used directly by application layer.
 */
public interface AccountRepository extends MongoRepository<AccountDocument, String> {
}
