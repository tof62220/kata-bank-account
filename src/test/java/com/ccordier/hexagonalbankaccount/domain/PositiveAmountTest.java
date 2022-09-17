package com.ccordier.hexagonalbankaccount.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

		PositiveAmount amount = assertDoesNotThrow(new ThrowingSupplier<PositiveAmount>() {
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

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> new PositiveAmount(null));

		assertEquals("The value of the amount is mandatory.", exception.getMessage());

	}

	@Test
	void testConstructorWithNegativeNumber() throws Exception {

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> new PositiveAmount(BigDecimal.valueOf(-1500)));

		assertEquals("The value of the amount must be a positive number.", exception.getMessage());

	}

	@Test
	void testHashCodeWithSameAmount() throws Exception {
		PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1234));
		PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(1234));

		assertTrue(amount1.hashCode() == amount1.hashCode());
	}

	@Test
	void testEqualsWithSameAmount() throws Exception {
		PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1234));
		PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(1234));

		assertTrue(amount1.equals(amount2));
	}

	@Test
	void testHashCodeWithNotSameAmount() throws Exception {
		PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1234));
		PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(4321));

		assertFalse(amount1.hashCode() == amount1.hashCode());
	}

	@Test
	void testEqualsWithNotSameAmount() throws Exception {
		PositiveAmount amount1 = new PositiveAmount(BigDecimal.valueOf(1234));
		PositiveAmount amount2 = new PositiveAmount(BigDecimal.valueOf(4321));

		assertFalse(amount1.equals(amount1));
	}

}
