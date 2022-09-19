package com.ccordier.hexagonalbankaccount.port.incoming;

import java.math.BigDecimal;
import java.util.UUID;


/**
 * Port Interface that defines the Withdrawal Use Case
 * 
 * @author c.cordier *
 */
public interface WithdrawUseCase {

	/**
	 * Make a withdraw on a bank account
	 * 
	 * @param id the identifier of the {@code BankAccount}
	 * @param amount The amount to be withdrawn from the {@code BankAccount}
	 */
	void withdraw(UUID id, BigDecimal amount);
}