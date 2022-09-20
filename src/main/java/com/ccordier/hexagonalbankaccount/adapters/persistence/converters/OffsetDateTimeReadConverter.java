package com.ccordier.hexagonalbankaccount.adapters.persistence.converters;

import java.time.OffsetDateTime;

import org.springframework.core.convert.converter.Converter;


/**
 * A converter class to convert a source object of type {@code String} to a target of type {@code OffsetDateTime}.
 * 
 * @author c.cordier
 */
public class OffsetDateTimeReadConverter implements Converter<String, OffsetDateTime> {

	@Override
	public OffsetDateTime convert(String source) {
		//return date.toInstant().atZone(ZoneOffset.UTC).toOffsetDateTime();
		return OffsetDateTime.parse(source);
	}

}
