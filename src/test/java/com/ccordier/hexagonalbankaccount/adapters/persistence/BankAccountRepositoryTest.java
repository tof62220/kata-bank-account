package com.ccordier.hexagonalbankaccount.adapters.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ccordier.hexagonalbankaccount.domain.AccountStatement;
import com.ccordier.hexagonalbankaccount.domain.BankAccount;
import com.ccordier.hexagonalbankaccount.domain.Customer;
import com.ccordier.hexagonalbankaccount.domain.PositiveAmount;


class BankAccountRepositoryTest {

	private SpringDataBankAccountRepository repository;
	private BankAccountRepository bankAccountRepository;

	@BeforeEach
	void setUp() {
		repository = mock(SpringDataBankAccountRepository.class);
		bankAccountRepository = new BankAccountRepository(repository);
	}

	@Test
	void testLoad() throws Exception {
		final UUID id = UUID.randomUUID();
		final BankAccount bankAccount = createBankAccount(id);
		when(repository.findById(id)).thenReturn(Optional.of(bankAccount));

		final Optional<BankAccount> result = bankAccountRepository.load(id);

		assertTrue(result.isPresent());
		assertEquals(bankAccount, result.get());
	}

	@Test
	void testLoadWithEmptyResult() throws Exception {
		final UUID id = UUID.randomUUID();
		when(repository.findById(id)).thenReturn(Optional.empty());

		final Optional<BankAccount> result = bankAccountRepository.load(id);

		assertTrue(result.isEmpty());
	}

	@Test
	void testSave() throws Exception {
		final UUID id = UUID.randomUUID();
		final BankAccount bankAccount = createBankAccount(id);

		bankAccountRepository.save(bankAccount);

		verify(repository).save(bankAccount);
	}

	private BankAccount createBankAccount(final UUID id) {
		final Customer customer = new Customer("0001", "Gaston", "Lagaffe");
		return new BankAccount(id,
				"0001",
				PositiveAmount.valueOf(BigDecimal.valueOf(1000)),
				customer,
				AccountStatement.getInstance());
	}

}
