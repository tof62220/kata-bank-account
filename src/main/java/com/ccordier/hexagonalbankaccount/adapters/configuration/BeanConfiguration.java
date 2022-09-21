package com.ccordier.hexagonalbankaccount.adapters.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ccordier.hexagonalbankaccount.HexagonalBankAccountApplication;
import com.ccordier.hexagonalbankaccount.adapters.persistence.BankAccountRepository;
import com.ccordier.hexagonalbankaccount.service.BankAccountService;


/**
 * ean Configuration Class
 * 
 * @author c.cordier
 *
 */
@Configuration
@ComponentScan(basePackageClasses = HexagonalBankAccountApplication.class)
public class BeanConfiguration {

	@Bean
	BankAccountService bankAccountService(final BankAccountRepository repository) {
		return new BankAccountService(repository, repository);
	}
}
