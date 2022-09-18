package com.ccordier.hexagonalbankaccount.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;


class OperationTest {

	@Test
	void testConstructorWithNotNullParameters() throws Exception {

		OffsetDateTime oprationDate = OffsetDateTime.now();

		Operation operation = assertDoesNotThrow(new ThrowingSupplier<Operation>() {
			@Override
			public Operation get() throws Throwable {
				return new Operation(oprationDate,
						OperationType.WITHDRAWAL,
						PositiveAmount.valueOf(BigDecimal.valueOf(500)),
						PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
			}
		});

		assertNotNull(operation);
		assertEquals(oprationDate, operation.getDate());
		assertEquals(OperationType.WITHDRAWAL, operation.getType());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(500)), operation.getAmount());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(1500)), operation.getBalanceAfterOperation());

	}

	@Test
	void testConstructorWithoutDate() throws Exception {

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Operation(null,
						OperationType.WITHDRAWAL,
						PositiveAmount.valueOf(BigDecimal.valueOf(500)),
						PositiveAmount.valueOf(BigDecimal.valueOf(1500))));

		assertEquals("date is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutType() throws Exception {

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Operation(OffsetDateTime.now(),
						null,
						PositiveAmount.valueOf(BigDecimal.valueOf(500)),
						PositiveAmount.valueOf(BigDecimal.valueOf(1500))));

		assertEquals("type is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutAmount() throws Exception {

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Operation(OffsetDateTime.now(),
						OperationType.WITHDRAWAL,
						null,
						PositiveAmount.valueOf(BigDecimal.valueOf(1500))));

		assertEquals("amount is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutBalance() throws Exception {

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Operation(OffsetDateTime.now(),
						OperationType.WITHDRAWAL,
						PositiveAmount.valueOf(BigDecimal.valueOf(500)),
						null));

		assertEquals("balanceAfterOperation is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testHashCodeWithSameOperation() throws Exception {
		OffsetDateTime oprationDate = OffsetDateTime.now();
		Operation opreration1 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
		Operation opreration2 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		assertTrue(opreration1.hashCode() == opreration2.hashCode());
	}

	@Test
	void testEqualsWithSameOperation() throws Exception {
		OffsetDateTime oprationDate = OffsetDateTime.now();
		Operation opreration1 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
		Operation opreration2 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		assertTrue(opreration1.equals(opreration2));
	}

	@Test
	void testHashCodeWithNotSameOperation() throws Exception {
		OffsetDateTime oprationDate = OffsetDateTime.now();
		Operation opreration1 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
		Operation opreration2 = new Operation(oprationDate,
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		assertFalse(opreration1.hashCode() == opreration2.hashCode());
	}

	@Test
	void testEqualsWithNotSameOperation() throws Exception {
		OffsetDateTime oprationDate = OffsetDateTime.now();
		Operation opreration1 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
		Operation opreration2 = new Operation(oprationDate,
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		assertFalse(opreration1.equals(opreration2));
	}

}
