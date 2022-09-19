package com.ccordier.hexagonalbankaccount.port.outgoing;

import java.util.Optional;
import java.util.UUID;

import com.ccordier.hexagonalbankaccount.domain.BankAccount;


/**
 * Port Interface to load a bank account
 * 
 * @author c.cordier
 *
 */
public interface LoadAccountPort {

	/**
	 * Load a bank account
	 * 
	 * @param id the identifier of the {@code BankAccount}
	 * @return an {@code Optional} with the the {@code BankAccount} if the bank account was found, otherwise an empty
	 * {@code Optional}
	 */
	Optional<BankAccount> load(UUID id);
}