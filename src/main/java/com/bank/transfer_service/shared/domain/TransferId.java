package com.bank.transfer_service.shared.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object representing a Transfer identifier.
 * Wraps a UUID and validates the format on construction.
 */
public final class TransferId {

	private final UUID id;

	public TransferId(String value) {
		Objects.requireNonNull(value, "transfer id must not be null");
		try {
			this.id = UUID.fromString(value);
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("invalid UUID for TransferId", ex);
		}
	}

	public static TransferId of(String value) {
		return new TransferId(value);
	}

	public String value() {
		return id.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TransferId that = (TransferId) o;
		return id.equals(that.id);
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
