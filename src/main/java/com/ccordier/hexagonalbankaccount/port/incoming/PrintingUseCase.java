package com.ccordier.hexagonalbankaccount.port.incoming;

import java.io.PrintStream;
import java.util.UUID;


/**
 * Port Interface that defines the Print Use Case
 * 
 * @author c.cordier
 *
 */
public interface PrintingUseCase {

	/**
	 * Print the bank account's operations ordered by date
	 * 
	 * @param id the identifier of the bank account
	 * @param printer the output stream
	 */
	void print(UUID id, PrintStream printer);
}