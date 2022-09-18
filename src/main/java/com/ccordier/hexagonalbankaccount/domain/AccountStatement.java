package com.ccordier.hexagonalbankaccount.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;


/**
 * The account statement on a bank account
 * 
 * @author c.cordier
 */
@EqualsAndHashCode
public class AccountStatement {

	private final List<Operation> operations;

	/**
	 * Constructs an account statement with a list of operations on a bank account
	 * 
	 * @param operations The list of operations on a bank account
	 */
	public AccountStatement(final List<Operation> operations) {
		Objects.requireNonNull(operations, "List of operations is mandatory.");
		this.operations = List.copyOf(operations);
	}

	/**
	 * Returns a new {@code AccountStatement} whose operations are existing operations from {@code (this)} and the new
	 * one from method parameter
	 * 
	 * @param operation The operation to be added to this {@code AccountStatement}
	 * @return a new {@code AccountStatement} with old operations and the new one
	 */
	public AccountStatement addOperation(final Operation operation) {
		final List<Operation> copyOfOperations = operations.stream().collect(Collectors.toList());
		copyOfOperations.add(operation);
		return new AccountStatement(copyOfOperations);
	}

	/**
	 * Returns the list of operations sorted by date on this {@code AccountStatement}
	 * 
	 * @return The list of operations sorted by date
	 */
	public List<Operation> getOperations() {
		return operations.stream().sorted().collect(Collectors.toUnmodifiableList());
	}

	/**
	 * Returns a new instance of {@code AccountStatement} with an empty list of {@code Operation}
	 * 
	 * @return a new instance of {@code AccountStatement}
	 */
	public static AccountStatement getInstance() {
		return new AccountStatement(new ArrayList<>());
	}

}
