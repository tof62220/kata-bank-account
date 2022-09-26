package com.ccordier.hexagonalbankaccount.service;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.ccordier.hexagonalbankaccount.domain.BankAccount;
import com.ccordier.hexagonalbankaccount.domain.Operation;
import com.ccordier.hexagonalbankaccount.domain.PositiveAmount;
import com.ccordier.hexagonalbankaccount.port.incoming.DepositUseCase;
import com.ccordier.hexagonalbankaccount.port.incoming.HistoryUseCase;
import com.ccordier.hexagonalbankaccount.port.incoming.PrintingUseCase;
import com.ccordier.hexagonalbankaccount.port.incoming.WithdrawUseCase;
import com.ccordier.hexagonalbankaccount.port.outgoing.LoadAccountPort;
import com.ccordier.hexagonalbankaccount.port.outgoing.SaveAccountPort;

import lombok.extern.slf4j.Slf4j;


/**
 * Service to manage bank accounts
 * 
 * @author c.cordier
 */
@Slf4j
public class BankAccountService implements DepositUseCase, WithdrawUseCase, HistoryUseCase, PrintingUseCase {

	private LoadAccountPort loadAccountPort;
	private SaveAccountPort saveAccountPort;

	public BankAccountService(final LoadAccountPort loadAccountPort, final SaveAccountPort saveAccountPort) {
		this.loadAccountPort = loadAccountPort;
		this.saveAccountPort = saveAccountPort;
	}

	@Override
	public void deposit(final UUID id, final BigDecimal amount) {
		log.info("Try to deposit amount on bank account with id = {}", id);
		final BankAccount account = loadAccountPort.load(id)
				.orElseThrow(NoSuchElementException::new);
		log.debug("Bank account with id = {} was found", id);
		final BankAccount updatedAccount = account.deposit(PositiveAmount.valueOf(amount));
		log.debug("Deposit on bank account with id = {} was realized", id);
		saveAccountPort.save(updatedAccount);
		log.info("Bank account with id = {} was saved after deposit", id);
	}

	@Override
	public void withdraw(final UUID id, final BigDecimal amount) {
		log.info("Try to withdraw amount on bank account with id = {}", id);
		final BankAccount account = loadAccountPort.load(id)
				.orElseThrow(NoSuchElementException::new);
		log.debug("Bank account with id = {} was found", id);
		final BankAccount updatedAccount = account.withdraw(PositiveAmount.valueOf(amount));
		log.debug("Withdrawal on bank account with id = {} was realized", id);
		saveAccountPort.save(updatedAccount);
		log.info("Bank account with id = {} was saved after withdrawal", id);
	}

	@Override
	public List<Operation> history(final UUID id) {
		log.info("Try to find bank account with id = {}", id);
		final BankAccount account = loadAccountPort.load(id)
				.orElseThrow(NoSuchElementException::new);
		log.info("Bank account with id = {} was found", id);
		return account.getHistory();
	}

	@Override
	public void print(UUID id, PrintStream printer) {
		BankAccount account = loadAccountPort.load(id)
				.orElseThrow(NoSuchElementException::new);

		account.printStatement(printer);
	}

}
