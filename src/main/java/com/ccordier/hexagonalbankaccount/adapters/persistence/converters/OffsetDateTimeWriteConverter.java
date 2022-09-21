package com.ccordier.hexagonalbankaccount.adapters.persistence.converters;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.core.convert.converter.Converter;


/**
 * A converter class to convert a source object of type {@code OffsetDateTime} to a target of type {@code String}.
 * 
 * @author c.cordier
 */
public class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, String> {

	@Override
	public String convert(final OffsetDateTime source) {
		return source.toInstant().atZone(ZoneOffset.UTC).toString();
	}
}
