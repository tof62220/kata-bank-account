package com.ccordier.hexagonalbankaccount.adapters.persistence.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;


class OffsetDateTimeReadConverterTest {

	@Test
	void testConvert() throws Exception {
		final OffsetDateTime result = OffsetDateTime.of(2022, 1, 2, 3, 4, 5, 6, ZoneOffset.UTC);

		final OffsetDateTimeReadConverter converter = new OffsetDateTimeReadConverter();

		final OffsetDateTime convertedDate = converter.convert("2022-01-02T03:04:05.000000006Z");

		assertNotNull(convertedDate);
		assertEquals(result, convertedDate);
	}

}
