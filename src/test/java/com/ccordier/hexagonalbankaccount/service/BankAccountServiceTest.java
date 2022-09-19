package com.ccordier.hexagonalbankaccount.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccordier.hexagonalbankaccount.adapters.persistence.BankAccountRepository;
import com.ccordier.hexagonalbankaccount.domain.AccountStatement;
import com.ccordier.hexagonalbankaccount.domain.BankAccount;
import com.ccordier.hexagonalbankaccount.domain.Customer;
import com.ccordier.hexagonalbankaccount.domain.Operation;
import com.ccordier.hexagonalbankaccount.domain.OperationType;
import com.ccordier.hexagonalbankaccount.domain.PositiveAmount;


@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

	private BankAccountRepository bankAccountRepository;
	private BankAccountService bankAccountService;

	@Captor
	ArgumentCaptor<BankAccount> bankAccountCaptor;

	@BeforeEach
	void setUp() {
		bankAccountRepository = mock(BankAccountRepository.class);
		bankAccountService = new BankAccountService(bankAccountRepository, bankAccountRepository);
	}

	@Test
	void testDepositThrowNoSuchElementException() throws Exception {
		final UUID id = UUID.randomUUID();
		when(bankAccountRepository.load(id)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class,
				() -> bankAccountService.deposit(id, BigDecimal.valueOf(500)));
	}

	@Test
	void testWithdrawThrowNoSuchElementException() throws Exception {
		final UUID id = UUID.randomUUID();
		when(bankAccountRepository.load(id)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class,
				() -> bankAccountService.withdraw(id, BigDecimal.valueOf(500)));
	}

	@Test
	void testHistoryThrowNoSuchElementException() throws Exception {
		final UUID id = UUID.randomUUID();
		when(bankAccountRepository.load(id)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class,
				() -> bankAccountService.history(id));
	}

	@Test
	void testDepositWithNullAmount() throws Exception {
		final UUID id = UUID.randomUUID();
		final BankAccount bankAccount = createBankAccount(id);
		when(bankAccountRepository.load(id)).thenReturn(Optional.of(bankAccount));

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> bankAccountService.deposit(id, null));

		assertEquals("The value of the amount is mandatory.", exception.getMessage());
	}

	@Test
	void testWithdrawWithNullAmount() throws Exception {
		final UUID id = UUID.randomUUID();
		final BankAccount bankAccount = createBankAccount(id);
		when(bankAccountRepository.load(id)).thenReturn(Optional.of(bankAccount));

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> bankAccountService.withdraw(id, null));

		assertEquals("The value of the amount is mandatory.", exception.getMessage());
	}

	@Test
	void testDepositWithNegativeAmount() throws Exception {
		final UUID id = UUID.randomUUID();
		final BankAccount bankAccount = createBankAccount(id);
		when(bankAccountRepository.load(id)).thenReturn(Optional.of(bankAccount));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> bankAccountService.deposit(id, BigDecimal.valueOf(-500)));

		assertEquals("The value of the amount must be a positive number.", exception.getMessage());
	}

	@Test
	void testWithdrawWithNegativeAmount() throws Exception {
		final UUID id = UUID.randomUUID();
		final BankAccount bankAccount = createBankAccount(id);
		when(bankAccountRepository.load(id)).thenReturn(Optional.of(bankAccount));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> bankAccountService.withdraw(id, BigDecimal.valueOf(-500)));

		assertEquals("The value of the amount must be a positive number.", exception.getMessage());
	}

	@Test
	void testWithdrawThrowArithmeticException() throws Exception {
		final UUID id = UUID.randomUUID();
		final BankAccount bankAccount = createBankAccount(id);
		when(bankAccountRepository.load(id)).thenReturn(Optional.of(bankAccount));

		ArithmeticException exception = assertThrows(ArithmeticException.class,
				() -> bankAccountService.withdraw(id, BigDecimal.valueOf(1500)));

		assertEquals("The amount to subtract is greater than this amount.", exception.getMessage());
	}

	@Test
	void testDeposit() throws Exception {
		final UUID id = UUID.randomUUID();
		final BankAccount bankAccount = spy(createBankAccount(id));
		final BigDecimal amount = BigDecimal.valueOf(500);

		when(bankAccountRepository.load(id)).thenReturn(Optional.of(bankAccount));

		bankAccountService.deposit(id, amount);

		verify(bankAccountRepository).load(id);
		verify(bankAccount).deposit(eq(PositiveAmount.valueOf(amount)));
		verify(bankAccountRepository).save(bankAccountCaptor.capture());

		final BankAccount updatedBankAccount = bankAccountCaptor.getValue();

		assertNotNull(updatedBankAccount);
		assertEquals(id, updatedBankAccount.getId());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(1500)), updatedBankAccount.getCurrentBalance());

	}

	@Test
	void testWithdraw() throws Exception {
		final UUID id = UUID.randomUUID();
		final BankAccount bankAccount = spy(createBankAccount(id));
		final BigDecimal amount = BigDecimal.valueOf(500);

		when(bankAccountRepository.load(id)).thenReturn(Optional.of(bankAccount));

		bankAccountService.withdraw(id, amount);

		verify(bankAccountRepository).load(id);
		verify(bankAccount).withdraw(eq(PositiveAmount.valueOf(amount)));
		verify(bankAccountRepository).save(bankAccountCaptor.capture());

		final BankAccount updatedBankAccount = bankAccountCaptor.getValue();

		assertNotNull(updatedBankAccount);
		assertEquals(id, updatedBankAccount.getId());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(500)), updatedBankAccount.getCurrentBalance());
	}

	@Test
	void testHistory() throws Exception {
		final UUID id = UUID.randomUUID();
		final BigDecimal amount = BigDecimal.valueOf(500);
		final BankAccount bankAccount = spy(
				createBankAccount(id).deposit(PositiveAmount.valueOf(amount)).withdraw(PositiveAmount.valueOf(amount)));

		when(bankAccountRepository.load(id)).thenReturn(Optional.of(bankAccount));

		List<Operation> result = bankAccountService.history(id);

		verify(bankAccountRepository).load(id);
		verify(bankAccount).getHistory();

		assertEquals(2, result.size());
		Operation deposit = result.get(0);

		assertEquals(OperationType.DEPOSIT, deposit.getType());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(500)), deposit.getAmount());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(1500)), deposit.getBalanceAfterOperation());

		Operation withdraw = result.get(1);

		assertEquals(OperationType.WITHDRAWAL, withdraw.getType());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(500)), withdraw.getAmount());
		assertEquals(PositiveAmount.valueOf(BigDecimal.valueOf(1000)), withdraw.getBalanceAfterOperation());
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
