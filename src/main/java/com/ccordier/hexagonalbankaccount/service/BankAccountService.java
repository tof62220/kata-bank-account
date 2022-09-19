package com.ccordier.hexagonalbankaccount.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.ccordier.hexagonalbankaccount.domain.BankAccount;
import com.ccordier.hexagonalbankaccount.domain.Operation;
import com.ccordier.hexagonalbankaccount.domain.PositiveAmount;
import com.ccordier.hexagonalbankaccount.port.incoming.DepositUseCase;
import com.ccordier.hexagonalbankaccount.port.incoming.HistoryUseCase;
import com.ccordier.hexagonalbankaccount.port.incoming.WithdrawUseCase;
import com.ccordier.hexagonalbankaccount.port.outgoing.LoadAccountPort;
import com.ccordier.hexagonalbankaccount.port.outgoing.SaveAccountPort;


/**
 * Service to manage bank accounts
 * 
 * @author c.cordier
 */
public class BankAccountService implements DepositUseCase, WithdrawUseCase, HistoryUseCase {

	private LoadAccountPort loadAccountPort;
	private SaveAccountPort saveAccountPort;

	public BankAccountService(LoadAccountPort loadAccountPort, SaveAccountPort saveAccountPort) {
		this.loadAccountPort = loadAccountPort;
		this.saveAccountPort = saveAccountPort;
	}

	@Override
	public void deposit(UUID id, BigDecimal amount) {
		BankAccount account = loadAccountPort.load(id)
				.orElseThrow(NoSuchElementException::new);

		BankAccount updatedAccount = account.deposit(PositiveAmount.valueOf(amount));

		saveAccountPort.save(updatedAccount);
	}

	@Override
	public void withdraw(UUID id, BigDecimal amount) {
		BankAccount account = loadAccountPort.load(id)
				.orElseThrow(NoSuchElementException::new);

		BankAccount updatedAccount = account.withdraw(PositiveAmount.valueOf(amount));

		saveAccountPort.save(updatedAccount);
	}

	@Override
	public List<Operation> history(UUID id) {
		BankAccount account = loadAccountPort.load(id)
				.orElseThrow(NoSuchElementException::new);

		return account.getHistory();
	}

}
