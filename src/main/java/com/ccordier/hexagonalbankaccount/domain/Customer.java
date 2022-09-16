package com.ccordier.hexagonalbankaccount.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


/**
 * A Customer of the bank
 * 
 * @author c.cordier
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Customer {

	@NonNull
	private final String clientNumber;

	@NonNull
	private final String firstname;

	@NonNull
	private final String lastname;

}
