package com.ccordier.hexagonalbankaccount;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ccordier.hexagonalbankaccount.adapters.persistence.BankAccountRepository;
import com.ccordier.hexagonalbankaccount.domain.AccountStatement;
import com.ccordier.hexagonalbankaccount.domain.BankAccount;
import com.ccordier.hexagonalbankaccount.domain.Customer;
import com.ccordier.hexagonalbankaccount.domain.PositiveAmount;


@SpringBootApplication
public class HexagonalBankAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexagonalBankAccountApplication.class, args);
	}

	@Bean
	public CommandLineRunner bootstrapData(final BankAccountRepository repository) {
		return (args) -> {
			final Customer customer = new Customer("0001", "Gastion", "Lagaffe");
			final BigDecimal initialBalance = BigDecimal.valueOf(1000);
			final BankAccount bankAccount = new BankAccount(UUID.fromString("c397b7f1-9d85-491b-96c8-f0bbe1981774"),
					"0001",
					PositiveAmount.valueOf(initialBalance),
					customer,
					AccountStatement.getInstance());
			repository.save(bankAccount);
		};
	}

}
