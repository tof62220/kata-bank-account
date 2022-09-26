package com.ccordier.hexagonalbankaccount.domain;

import java.io.PrintStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

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

	private static final String SEPARATOR = " | ";
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

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

	/**
	 * Prints operation in an output stream
	 * 
	 * @param printer the output stream
	 */
	public void printTo(final PrintStream printer) {
		StringBuilder builder = new StringBuilder();
		builder.append(date.format(DATE_FORMATTER))
				.append(SEPARATOR)
				.append(type.name())
				.append(SEPARATOR)
				.append(amount.getValue().toString())
				.append(SEPARATOR)
				.append(balanceAfterOperation.getValue().toString());
		printer.println(builder.toString());
	}

}
