package com.bank.transfer_service.shared.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object representing an Account identifier.
 * Wraps a UUID and validates the format on construction.
 */
public final class AccountId {

	private final UUID id;

	public AccountId(String value) {
		Objects.requireNonNull(value, "account id must not be null");
		try {
			this.id = UUID.fromString(value);
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("invalid UUID for AccountId", ex);
		}
	}

	public static AccountId of(String value) {
		return new AccountId(value);
	}

	public String value() {
		return id.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AccountId accountId = (AccountId) o;
		return id.equals(accountId.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return id.toString();
	}
}
