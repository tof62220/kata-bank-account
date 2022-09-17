package com.ccordier.hexagonalbankaccount.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * The {@code PositiveAmount} class represents a immutable positive decimal number
 * and provides provides operations to manage amount.
 * 
 * @author c.cordier
 */
@Getter
@EqualsAndHashCode
public class PositiveAmount {

	private static final int AMOUNT_SCALE = 2;

	private final BigDecimal value;

	/**
	 * Constructs a PositiveAmount
	 * 
	 * @param value The value of the amount
	 * @throws NullPointerException if the value is null
	 * @throws IllegalArgumentException if the value is a negative number
	 */
	public PositiveAmount(final BigDecimal value) {
		validateAmount(value);
		this.value = value.setScale(AMOUNT_SCALE, RoundingMode.HALF_EVEN);
	}

	/**
	 * Returns a {@code PositiveAmount} whose value is {@code (this +
	 * otherAmount)}
	 * 
	 * @param otherAmount The amount to be added to this {@code PositiveAmount}
	 * @return {@code this + otherAmount}
	 */
	public PositiveAmount add(final PositiveAmount otherAmount) {
		return new PositiveAmount(this.value.add(otherAmount.value).setScale(AMOUNT_SCALE, RoundingMode.HALF_EVEN));
	}

	/**
	 * Returns a {@code PositiveAmount} whose value is {@code (this -
	 * otherAmount)}
	 * 
	 * @param otherAmount The amount to be subtracted frto this {@code PositiveAmount}
	 * @return {@code this - otherAmount}
	 * @throws ArithmeticException if {@code otherAmount} is greater than {@code this}
	 */
	public PositiveAmount subtract(final PositiveAmount otherAmount) {
		if (this.value.compareTo(otherAmount.value) < 0) {
			throw new ArithmeticException("The amount to subtract is greater than this amount.");
		}
		return new PositiveAmount(
				this.value.subtract(otherAmount.value).setScale(AMOUNT_SCALE, RoundingMode.HALF_EVEN));
	}

	/**
	 * Validates the value of the positive amount
	 * 
	 * @param value The value of the amount
	 * @throws NullPointerException if the value is null
	 * @throws IllegalArgumentException if the value is a negative number
	 */
	private static void validateAmount(final BigDecimal value) {
		Objects.requireNonNull(value, "The value of the amount is mandatory.");
		if (value.signum() == -1) {
			throw new IllegalArgumentException("The value of the amount must be a positive number.");
		}
	}

}
