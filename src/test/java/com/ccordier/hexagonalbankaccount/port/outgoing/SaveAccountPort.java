package com.ccordier.hexagonalbankaccount.port.outgoing;

import com.ccordier.hexagonalbankaccount.domain.BankAccount;


/**
 * Port Interface to save a bank account
 * 
 * @author c.cordier
 *
 */
public interface SaveAccountPort {

	/**
	 * Save a bank account
	 * 
	 * @param bankAccount the bank account to be saved
	 */
	void save(BankAccount bankAccount);
}