package com.ccordier.hexagonalbankaccount.port.incoming;

import java.math.BigDecimal;
import java.util.UUID;


/**
 * Port Interface that defines the Deposit Use Case
 * 
 * @author c.cordier
 *
 */
public interface DepositUseCase {

	/**
	 * Makes a deposit in a bank account
	 * 
	 * @param id the identifier of the {@code BankAccount}
	 * @param amount The amount to be deposited on the {@code BankAccount}
	 */
	void deposit(UUID id, BigDecimal amount);
}