package com.ccordier.hexagonalbankaccount.adapters.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.ccordier.hexagonalbankaccount.adapters.persistence.converters.OffsetDateTimeReadConverter;
import com.ccordier.hexagonalbankaccount.adapters.persistence.converters.OffsetDateTimeWriteConverter;


/**
 * MongoDB Configuration Class
 * 
 * @author c.cordier
 *
 */
@Configuration
public class MongoConfig {

	/**
	 * Sets the custom conversion for MongDB
	 * 
	 * @return Value object to capture custom conversion
	 */
	@Bean
	public MongoCustomConversions customConversions() {
		return new MongoCustomConversions(
				List.of(new OffsetDateTimeReadConverter(),
						new OffsetDateTimeWriteConverter()));
	}

}
