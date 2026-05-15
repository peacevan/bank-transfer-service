package com.bank.transfer_service.shared.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountIdTest {

    @Test
    void validUuidCreatesAccountId() {
        String uuid = UUID.randomUUID().toString();
        AccountId id = new AccountId(uuid);
        assertEquals(uuid, id.value());
        assertEquals(uuid, id.toString());
    }

    @Test
    void invalidUuidThrows() {
        assertThrows(IllegalArgumentException.class, () -> new AccountId("not-a-uuid"));
    }

    @Test
    void equalsAndHashCode() {
        String uuid = UUID.randomUUID().toString();
        AccountId a = AccountId.of(uuid);
        AccountId b = AccountId.of(uuid);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
