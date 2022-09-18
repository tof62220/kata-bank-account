package com.ccordier.hexagonalbankaccount.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;


class AccountStatementTest {

	@Test
	void testConstructorWithNotNullParameters() throws Exception {

		AccountStatement accountStatement = assertDoesNotThrow(new ThrowingSupplier<AccountStatement>() {
			@Override
			public AccountStatement get() throws Throwable {
				return new AccountStatement(new ArrayList<>());
			}
		});

		assertNotNull(accountStatement);
	}

	@Test
	void testConstructorWithNullParametersr() throws Exception {

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new AccountStatement(null));

		assertEquals("List of operations is mandatory.", exception.getMessage());

	}

	@Test
	void testHashCodeWithSameAccountStatement() throws Exception {
		Operation opreration1 = new Operation(OffsetDateTime.now(),
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(2000)));
		Operation opreration2 = new Operation(OffsetDateTime.now(),
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		AccountStatement accountStatement1 = new AccountStatement(List.of(opreration1, opreration2));
		AccountStatement accountStatement2 = new AccountStatement(List.of(opreration1, opreration2));

		assertTrue(accountStatement1.hashCode() == accountStatement2.hashCode());
	}

	@Test
	void testEqualsWithSameAccountStatement() throws Exception {
		Operation opreration1 = new Operation(OffsetDateTime.now(),
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(2000)));
		Operation opreration2 = new Operation(OffsetDateTime.now(),
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		AccountStatement accountStatement1 = new AccountStatement(List.of(opreration1, opreration2));
		AccountStatement accountStatement2 = new AccountStatement(List.of(opreration1, opreration2));

		assertTrue(accountStatement1.equals(accountStatement2));
	}

	@Test
	void testHashCodeWithNotSameAccountStatement() throws Exception {
		Operation opreration1 = new Operation(OffsetDateTime.now(),
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(2000)));
		Operation opreration2 = new Operation(OffsetDateTime.now(),
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		AccountStatement accountStatement1 = new AccountStatement(List.of(opreration1));
		AccountStatement accountStatement2 = new AccountStatement(List.of(opreration2));

		assertFalse(accountStatement1.hashCode() == accountStatement2.hashCode());
	}

	@Test
	void testEqualsWithNotSameAccountStatement() throws Exception {
		Operation opreration1 = new Operation(OffsetDateTime.now(),
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(2000)));
		Operation opreration2 = new Operation(OffsetDateTime.now(),
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		AccountStatement accountStatement1 = new AccountStatement(List.of(opreration1));
		AccountStatement accountStatement2 = new AccountStatement(List.of(opreration2));

		assertFalse(accountStatement1.equals(accountStatement2));
	}

	@Test
	void testGetOperations() throws Exception {
		OffsetDateTime date1 = OffsetDateTime.of(2022, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);
		OffsetDateTime date2 = OffsetDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneOffset.UTC);

		Operation opreration1 = new Operation(date1,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(2000)));
		Operation opreration2 = new Operation(date2,
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		AccountStatement accountStatement = new AccountStatement(List.of(opreration2, opreration1));

		List<Operation> sortedOperations = accountStatement.getOperations();

		assertNotNull(sortedOperations);
		assertTrue(sortedOperations.size() == 2);
		assertEquals(opreration1, sortedOperations.get(0));
		assertEquals(opreration2, sortedOperations.get(1));

	}

	@Test
	void testAddOperation() throws Exception {
		OffsetDateTime date1 = OffsetDateTime.of(2022, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);
		OffsetDateTime date2 = OffsetDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneOffset.UTC);

		Operation opreration1 = new Operation(date1,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(2000)));
		Operation opreration2 = new Operation(date2,
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		AccountStatement accountStatement = new AccountStatement(List.of(opreration1));

		AccountStatement updatedAccountStatement = accountStatement.addOperation(opreration2);

		assertNotNull(updatedAccountStatement);
		assertFalse(accountStatement.hashCode() == updatedAccountStatement.hashCode());
		assertFalse(accountStatement.equals(updatedAccountStatement));

		List<Operation> oldSortedOperations = accountStatement.getOperations();

		assertNotNull(oldSortedOperations);
		assertTrue(oldSortedOperations.size() == 1);
		assertEquals(opreration1, oldSortedOperations.get(0));

		List<Operation> updatedSortedOperations = updatedAccountStatement.getOperations();

		assertNotNull(updatedSortedOperations);
		assertTrue(updatedSortedOperations.size() == 2);
		assertEquals(opreration1, updatedSortedOperations.get(0));
		assertEquals(opreration2, updatedSortedOperations.get(1));
	}

}