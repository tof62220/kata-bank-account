package com.ccordier.hexagonalbankaccount.adapters.persistence.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;


class OffsetDateTimeWriteConverterTest {

	@Test
	void testConvert() throws Exception {
		final OffsetDateTime dateToConvert = OffsetDateTime.of(2022, 1, 2, 3, 4, 5, 6, ZoneOffset.UTC);

		final OffsetDateTimeWriteConverter converter = new OffsetDateTimeWriteConverter();

		final String convertedDate = converter.convert(dateToConvert);

		assertNotNull(convertedDate);
		assertEquals("2022-01-02T03:04:05.000000006Z", convertedDate);
	}

}
