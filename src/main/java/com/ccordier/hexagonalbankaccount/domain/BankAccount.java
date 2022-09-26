package com.ccordier.hexagonalbankaccount.domain;

import java.io.PrintStream;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


/**
 * A bank account for a customer
 * 
 * @author c.cordier
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class BankAccount {

	@NonNull
	private final UUID id;

	@NonNull
	private final String accountNumber;

	@NonNull
	private final PositiveAmount currentBalance;

	@NonNull
	private final Customer customer;

	@NonNull
	private final AccountStatement statement;

	/**
	 * Returns all operations on this {@code BankAccount} ordered by date
	 * 
	 * @return The list of operations ordered by date
	 */
	public List<Operation> getHistory() {
		return this.statement.getOperations();
	}

	/**
	 * Makes a deposit on this {@code BankAccount}
	 * 
	 * @param amount The amount to be deposited on this {@code BankAccount}
	 * @return the updated {@code BankAccount} with a new operation and updated balance
	 */
	public BankAccount deposit(final PositiveAmount amount) {
		final PositiveAmount updatedBalance = currentBalance.add(amount);
		final Operation deposit = new Operation(OffsetDateTime.now(),
				OperationType.DEPOSIT,
				amount,
				updatedBalance);
		final AccountStatement updatedAccountStatement = statement.addOperation(deposit);
		return new BankAccount(id, accountNumber, updatedBalance, customer, updatedAccountStatement);
	}

	/**
	 * Makes a withdrawal from {@code BankAccount}
	 * 
	 * @param amount The amount to be withdrawn from this {@code BankAccount}
	 * @return the updated {@code BankAccount} with a new operation and updated balance
	 */
	public BankAccount withdraw(final PositiveAmount amount) {
		final PositiveAmount updatedBalance = currentBalance.subtract(amount);
		final Operation withdrawal = new Operation(OffsetDateTime.now(),
				OperationType.WITHDRAWAL,
				amount,
				updatedBalance);
		final AccountStatement updatedAccountStatement = statement.addOperation(withdrawal);
		return new BankAccount(id, accountNumber, updatedBalance, customer, updatedAccountStatement);
	}

	/**
	 * Prints statement account in an output stream
	 * 
	 * @param printer the output stream
	 */
	public void printStatement(final PrintStream printer) {
		statement.printTo(printer);
	}

}
