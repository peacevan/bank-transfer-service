package com.bank.transfer_service.shared.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void createNonNegativeAmount() {
        Money m = new Money(new BigDecimal("10.00"), "BRL");
        assertEquals(new BigDecimal("10.00"), m.amount());
        assertEquals("BRL", m.currency());
    }

    @Test
    void createNegativeAmountThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Money(new BigDecimal("-1.00"), "BRL"));
    }

    @Test
    void plusSameCurrencyAdds() {
        Money a = new Money(new BigDecimal("10.00"), "BRL");
        Money b = new Money(new BigDecimal("5.50"), "BRL");
        Money sum = a.plus(b);
        assertEquals(new Money(new BigDecimal("15.50"), "BRL"), sum);
    }

    @Test
    void plusDifferentCurrencyThrows() {
        Money a = new Money(new BigDecimal("10.00"), "BRL");
        Money b = new Money(new BigDecimal("5.00"), "USD");
        assertThrows(IllegalArgumentException.class, () -> a.plus(b));
    }

    @Test
    void minusWithSufficientBalance() {
        Money a = new Money(new BigDecimal("10.00"), "BRL");
        Money b = new Money(new BigDecimal("4.25"), "BRL");
        Money diff = a.minus(b);
        assertEquals(new Money(new BigDecimal("5.75"), "BRL"), diff);
    }

    @Test
    void minusInsufficientThrows() {
        Money a = new Money(new BigDecimal("3.00"), "BRL");
        Money b = new Money(new BigDecimal("5.00"), "BRL");
        assertThrows(IllegalArgumentException.class, () -> a.minus(b));
    }
}
