package com.ccordier.hexagonalbankaccount.domain;

import java.time.OffsetDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


/**
 * An operation on a bank account
 * 
 * @author c.cordier
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Operation implements Comparable<Operation> {

	@NonNull
	private final OffsetDateTime date;

	@NonNull
	private final OperationType type;

	@NonNull
	private final PositiveAmount amount;

	@NonNull
	private final PositiveAmount balanceAfterOperation;

	/**
	 * Compares this object with the specified object for operation date order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * 
	 * @return a negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 */
	@Override
	public int compareTo(Operation otherOperation) {
		return this.date.compareTo(otherOperation.date);
	}

}
