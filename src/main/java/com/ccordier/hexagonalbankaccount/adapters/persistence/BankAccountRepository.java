package com.ccordier.hexagonalbankaccount.adapters.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ccordier.hexagonalbankaccount.domain.BankAccount;
import com.ccordier.hexagonalbankaccount.port.outgoing.LoadAccountPort;
import com.ccordier.hexagonalbankaccount.port.outgoing.SaveAccountPort;


/**
 * Repository Implementation to manage Bank Account
 * 
 * @author c.cordier
 */
@Component
public class BankAccountRepository implements LoadAccountPort, SaveAccountPort {

	private SpringDataBankAccountRepository repository;

	public BankAccountRepository(SpringDataBankAccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<BankAccount> load(UUID id) {
		return repository.findById(id);
	}

	@Override
	public void save(BankAccount bankAccount) {
		repository.save(bankAccount);
	}
}
