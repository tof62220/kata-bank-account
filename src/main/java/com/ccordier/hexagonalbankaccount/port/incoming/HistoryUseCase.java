package com.ccordier.hexagonalbankaccount.port.incoming;

import java.util.List;
import java.util.UUID;

import com.ccordier.hexagonalbankaccount.domain.Operation;


/**
 * Port Interface that defines the history Use Case
 * 
 * @author c.cordier
 */
public interface HistoryUseCase {

	/**
	 * Returns the bank account's operations ordered by date
	 * 
	 * @param id the identifier of the {@code BankAccount}
	 * @return a list of the operations ordered by date
	 */
	List<Operation> history(UUID id);
}