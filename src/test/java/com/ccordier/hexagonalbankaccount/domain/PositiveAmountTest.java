package com.ccordier.hexagonalbankaccount.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;


class PositiveAmountTest {

	@Test
	void testConstructorWithNotNullParameter() throws Exception {

		final PositiveAmount amount = assertDoesNotThrow(new ThrowingSupplier<PositiveAmount>() {
			@Override
			public PositiveAmount get() throws Throwable {
				return new PositiveAmount(BigDecimal.valueOf(1500));
			}
		});

		assertNotNull(amount);
		assertEquals(BigDecimal.valueOf(1500).setScale(2, RoundingMode.HALF_EVEN), amount.getValue());

	}

	@Test
	void testConstructorWithNullParameter() throws Exception {

		final NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new PositiveAmount(null));

		assertEquals("The value of the amount is mandatory.", exception.getMessage());

	}

	@Test
	void testConstructorWithNegativeNumber() throws Exception {

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> new PositiveAmount(BigDecimal.valueOf(-1500)));

		assertEquals("The value of the amount must be a positive number.", exception.getMessage());

	}

	@Test
	void testHashCodeWithSameAmount() throws Exception {
		final PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1234));
		final PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(1234));

		assertTrue(amount1.hashCode() == amount2.hashCode());
	}

	@Test
	void testEqualsWithSameAmount() throws Exception {
		final PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1234));
		final PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(1234));

		assertTrue(amount1.equals(amount2));
	}

	@Test
	void testHashCodeWithNotSameAmount() throws Exception {
		final PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1234));
		final PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(4321));

		assertFalse(amount1.hashCode() == amount2.hashCode());
	}

	@Test
	void testEqualsWithNotSameAmount() throws Exception {
		final PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1234));
		final PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(4321));

		assertFalse(amount1.equals(amount2));
	}

	@Test
	void testAdd() throws Exception {
		final PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1500));
		final PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(500));

		final PositiveAmount result = amount1.add(amount2);

		assertNotNull(result);
		assertEquals(BigDecimal.valueOf(2000).setScale(2, RoundingMode.HALF_EVEN), result.getValue());
		assertNotEquals(amount1, result);
		assertNotEquals(amount2, result);
	}

	@Test
	void testSubtract() throws Exception {
		final PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1500));
		final PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(500));

		final PositiveAmount result = amount1.subtract(amount2);

		assertNotNull(result);
		assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_EVEN), result.getValue());
		assertNotEquals(amount1, result);
		assertNotEquals(amount2, result);
	}

	@Test
	void testSubtractThrowArithmeticException() throws Exception {
		final PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(500));
		final PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(1500));

		final ArithmeticException exception = assertThrows(ArithmeticException.class,
				() -> amount1.subtract(amount2));

		assertEquals("The amount to subtract is greater than this amount.", exception.getMessage());
	}

}
