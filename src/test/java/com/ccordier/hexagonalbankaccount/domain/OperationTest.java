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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;


class OperationTest {

	@Test
	void testConstructorWithNotNullParameters() throws Exception {

		final OffsetDateTime oprationDate = OffsetDateTime.now();

		final Operation operation = assertDoesNotThrow(new ThrowingSupplier<Operation>() {
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

		final NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Operation(null,
						OperationType.WITHDRAWAL,
						PositiveAmount.valueOf(BigDecimal.valueOf(500)),
						PositiveAmount.valueOf(BigDecimal.valueOf(1500))));

		assertEquals("date is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutType() throws Exception {

		final NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Operation(OffsetDateTime.now(),
						null,
						PositiveAmount.valueOf(BigDecimal.valueOf(500)),
						PositiveAmount.valueOf(BigDecimal.valueOf(1500))));

		assertEquals("type is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutAmount() throws Exception {

		final NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Operation(OffsetDateTime.now(),
						OperationType.WITHDRAWAL,
						null,
						PositiveAmount.valueOf(BigDecimal.valueOf(1500))));

		assertEquals("amount is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testConstructorWithoutBalance() throws Exception {

		final NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new Operation(OffsetDateTime.now(),
						OperationType.WITHDRAWAL,
						PositiveAmount.valueOf(BigDecimal.valueOf(500)),
						null));

		assertEquals("balanceAfterOperation is marked non-null but is null", exception.getMessage());

	}

	@Test
	void testHashCodeWithSameOperation() throws Exception {
		final OffsetDateTime oprationDate = OffsetDateTime.now();
		final Operation opreration1 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
		final Operation opreration2 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		assertTrue(opreration1.hashCode() == opreration2.hashCode());
	}

	@Test
	void testEqualsWithSameOperation() throws Exception {
		final OffsetDateTime oprationDate = OffsetDateTime.now();
		final Operation opreration1 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
		final Operation opreration2 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		assertTrue(opreration1.equals(opreration2));
	}

	@Test
	void testHashCodeWithNotSameOperation() throws Exception {
		final OffsetDateTime oprationDate = OffsetDateTime.now();
		final Operation opreration1 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
		final Operation opreration2 = new Operation(oprationDate,
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		assertFalse(opreration1.hashCode() == opreration2.hashCode());
	}

	@Test
	void testEqualsWithNotSameOperation() throws Exception {
		final OffsetDateTime oprationDate = OffsetDateTime.now();
		final Operation opreration1 = new Operation(oprationDate,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
		final Operation opreration2 = new Operation(oprationDate,
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		assertFalse(opreration1.equals(opreration2));
	}

	@Test
	void testCompareTo() throws Exception {
		final OffsetDateTime date1 = OffsetDateTime.of(2022, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);
		final OffsetDateTime date2 = OffsetDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneOffset.UTC);

		final Operation opreration1 = new Operation(date1,
				OperationType.WITHDRAWAL,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));
		final Operation opreration2 = new Operation(date2,
				OperationType.DEPOSIT,
				PositiveAmount.valueOf(BigDecimal.valueOf(500)),
				PositiveAmount.valueOf(BigDecimal.valueOf(1500)));

		assertTrue(opreration1.compareTo(opreration2) == -1);
		assertTrue(opreration2.compareTo(opreration1) == 1);
	}

}
