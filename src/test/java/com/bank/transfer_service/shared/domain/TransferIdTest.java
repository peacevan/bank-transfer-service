package com.bank.transfer_service.shared.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransferIdTest {

    @Test
    void validUuidCreatesTransferId() {
        String uuid = UUID.randomUUID().toString();
        TransferId id = new TransferId(uuid);
        assertEquals(uuid, id.value());
        assertEquals(uuid, id.toString());
    }

    @Test
    void invalidUuidThrows() {
        assertThrows(IllegalArgumentException.class, () -> new TransferId("1234"));
    }

    @Test
    void equalsAndHashCode() {
        String uuid = UUID.randomUUID().toString();
        TransferId a = TransferId.of(uuid);
        TransferId b = TransferId.of(uuid);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
