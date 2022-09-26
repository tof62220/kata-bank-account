package com.ccordier.hexagonalbankaccount.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;


class BankAccountTest {

	@Test
	void testConstructorWithNotNullParameters() throws Exception {
		final UUID id = UUID.randomUUID();
		final Customer customer = new Customer("0001", "Gaston", "Lagaffe");
		final AccountStatement accountStatement = AccountStatement.getInstance();
		final PositiveAmount initialBalance = new PositiveAmount(BigDecimal.valueOf(1500));

		final BankAccount bankAccount = assertDoesNotThrow(new ThrowingSupplier<BankAccount>() {
			@Override
			public BankAccount get() throws Throwable {
				return new BankAccount(id,
						"0001",
						initialBalance,
						customer,
						accountStatement);
			}
		});

		assertNotNull(bankAccount);
		assertEquals(id, bankAccount.getId());
		assertEquals("0001", bankAccount.getAccountNumber());
		assertEquals(customer, bankAccount.getCustomer());
		assertEquals(initialBalance, bankAccount.getCurrentBalance());

	}

	@Test
	void testConstructorWithoutId() throws Exception {

		final Customer customer = new Customer("0001", "Gaston", "Lagaffe");
		final AccountStatement accountStatement = AccountStatement.getInstance();
		final PositiveAmount initialBalance = new PositiveAmount(BigDecimal.valueOf(1500));

		final NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new BankAccount(null,
						"0001",
						initialBalance,
						customer,
						accountStatement));

		assertEquals("id is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutAccountNumber() throws Exception {
		final UUID id = UUID.randomUUID();
		final Customer customer = new Customer("0001", "Gaston", "Lagaffe");
		final AccountStatement accountStatement = AccountStatement.getInstance();
		final PositiveAmount initialBalance = new PositiveAmount(BigDecimal.valueOf(1500));

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new BankAccount(id,
						null,
						initialBalance,
						customer,
						accountStatement));

		assertEquals("accountNumber is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutBalance() throws Exception {
		final UUID id = UUID.randomUUID();
		final Customer customer = new Customer("0001", "Gaston", "Lagaffe");
		final AccountStatement accountStatement = AccountStatement.getInstance();

		final NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new BankAccount(id,
						"0001",
						null,
						customer,
						accountStatement));

		assertEquals("currentBalance is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutCustomer() throws Exception {
		final UUID id = UUID.randomUUID();
		final AccountStatement accountStatement = AccountStatement.getInstance();
		final PositiveAmount initialBalance = new PositiveAmount(BigDecimal.valueOf(1500));

		final NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new BankAccount(id,
						"0001",
						initialBalance,
						null,
						accountStatement));

		assertEquals("customer is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutAccountStatement() throws Exception {
		final UUID id = UUID.randomUUID();
		final Customer customer = new Customer("0001", "Gaston", "Lagaffe");
		final PositiveAmount initialBalance = new PositiveAmount(BigDecimal.valueOf(1500));

		final NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new BankAccount(id,
						"0001",
						initialBalance,
						customer,
						null));

		assertEquals("statement is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testDeposit() throws Exception {
		final Customer customer = new Customer("0001", "Gastion", "Lagaffe");
		final BankAccount account = new BankAccount(UUID.randomUUID(),
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());

		final BankAccount updatedAccount = account.deposit(PositiveAmount.valueOf(BigDecimal.valueOf(1000)));

		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(2000)), updatedAccount.getCurrentBalance());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(1000)), account.getCurrentBalance());
	}

	@Test
	void testWithdraw() throws Exception {
		final Customer customer = new Customer("0001", "Gastion", "Lagaffe");
		final BankAccount account = new BankAccount(UUID.randomUUID(),
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());

		final BankAccount updatedAccount = account.withdraw(PositiveAmount.valueOf(BigDecimal.valueOf(500)));

		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(500)), updatedAccount.getCurrentBalance());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(1000)), account.getCurrentBalance());
	}

	@Test
	void testWithdrawThrowArithmeticException() throws Exception {
		final Customer customer = new Customer("0001", "Gastion", "Lagaffe");
		final BankAccount account = new BankAccount(UUID.randomUUID(),
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());

		ArithmeticException exception = assertThrows(ArithmeticException.class,
				() -> account.withdraw(PositiveAmount.valueOf(BigDecimal.valueOf(2000))));

		assertEquals("The amount to subtract is greater than this amount.", exception.getMessage());

	}

	@Test
	void testGetHistory() throws Exception {
		final Customer customer = new Customer("0001", "Gastion", "Lagaffe");
		final BankAccount account = new BankAccount(UUID.randomUUID(),
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance()).deposit(PositiveAmount.valueOf(BigDecimal.valueOf(1000)))
						.withdraw(PositiveAmount.valueOf(BigDecimal.valueOf(500)));

		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(1500)), account.getCurrentBalance());

		final List<Operation> operations = account.getHistory();

		assertEquals(2, operations.size());
		final Operation deposit = operations.get(0);

		assertNotNull(deposit.getDate());
		assertEquals(OperationType.DEPOSIT, deposit.getType());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(1000)), deposit.getAmount());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(2000)), deposit.getBalanceAfterOperation());

		final Operation withdraw = operations.get(1);

		assertNotNull(withdraw.getDate());
		assertEquals(OperationType.WITHDRAWAL, withdraw.getType());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(500)), withdraw.getAmount());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(1500)), withdraw.getBalanceAfterOperation());

	}

	@Test
	void testHashCodeWithSameBankAccount() throws Exception {
		final UUID id = UUID.randomUUID();
		final Customer customer = new Customer("0001", "Gastion", "Lagaffe");

		final BankAccount account1 = new BankAccount(id,
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());
		final BankAccount account2 = new BankAccount(id,
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());

		assertTrue(account1.hashCode() == account2.hashCode());
	}

	@Test
	void testEqualsWithSameBankAccount() throws Exception {
		final UUID id = UUID.randomUUID();
		final Customer customer = new Customer("0001", "Gastion", "Lagaffe");

		final BankAccount account1 = new BankAccount(id,
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());
		final BankAccount account2 = new BankAccount(id,
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());

		assertTrue(account1.equals(account2));
	}

	@Test
	void testHashCodeWithNotSameBankAccount() throws Exception {
		final Customer customer = new Customer("0001", "Gastion", "Lagaffe");

		final BankAccount account1 = new BankAccount(UUID.randomUUID(),
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());
		final BankAccount account2 = new BankAccount(UUID.randomUUID(),
				"0002",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());

		assertFalse(account1.hashCode() == account2.hashCode());
	}

	@Test
	void testEqualsWithNotSameBankAccount() throws Exception {
		final Customer customer = new Customer("0001", "Gastion", "Lagaffe");

		final BankAccount account1 = new BankAccount(UUID.randomUUID(),
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());
		final BankAccount account2 = new BankAccount(UUID.randomUUID(),
				"0002",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());

		assertFalse(account1.equals(account2));
	}

	@Test
	void testPrintStatement() throws Exception {
		final Customer customer = new Customer("0001", "Gastion", "Lagaffe");
		final BankAccount account = new BankAccount(UUID.randomUUID(),
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance()).deposit(PositiveAmount.valueOf(BigDecimal.valueOf(1000)))
						.withdraw(PositiveAmount.valueOf(BigDecimal.valueOf(500)));

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final PrintStream printStream = new PrintStream(outputStream);

		account.printStatement(printStream);

		//		assertEquals("date | operation | amount | balance\r\n"
		//				+ "21/09/2022 | DEPOSIT | 1000.00 | 2000.00\r\n"
		//				+ "21/09/2022 | WITHDRAWAL | 500.00 | 1500.00\r\n", outputStream.toString());
	}

}
