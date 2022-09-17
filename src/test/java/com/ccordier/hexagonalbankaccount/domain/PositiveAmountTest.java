package com.ccordier.hexagonalbankaccount.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

}
