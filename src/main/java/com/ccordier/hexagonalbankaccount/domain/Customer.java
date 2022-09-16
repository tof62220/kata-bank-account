package com.ccordier.hexagonalbankaccount.domain;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * A Customer of the bank
 * 
 * @author c.cordier
 */
@Getter
@EqualsAndHashCode
public class Customer {

	private final String clientNumber;

	private final String firstname;

	private final String lastname;

	/**
	 * Constructs a customer
	 * 
	 * @param clientNumber The number of the customer
	 * @param firstname The firstname of the customer
	 * @param lastname The lastname of the customer
	 */
	public Customer(final String clientNumber, final String firstname, final String lastname) {
		this.clientNumber = Objects.requireNonNull(clientNumber, "Client Number is mandatory.");
		this.firstname = Objects.requireNonNull(firstname, "Firstname is mandatory.");
		this.lastname = Objects.requireNonNull(lastname, "Lastname is mandatory.");
	}

}
