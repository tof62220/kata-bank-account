package com.ccordier.hexagonalbankaccount.domain;

import java.time.OffsetDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


/**
 * An operation on a bank account
 * 
 * @author c.cordier
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Operation {

	@NonNull
	private final OffsetDateTime date;

	@NonNull
	private final OperationType type;

	@NonNull
	private final PositiveAmount amount;

	@NonNull
	private final PositiveAmount balanceAfterOperation;

}
