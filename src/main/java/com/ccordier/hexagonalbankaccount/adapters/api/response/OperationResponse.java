package com.ccordier.hexagonalbankaccount.adapters.api.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.ccordier.hexagonalbankaccount.domain.Operation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


/**
 * OperationResponse Class used in REST Api
 * 
 * @author c.cordier
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class OperationResponse {

	@NonNull
	private final OffsetDateTime date;

	@NonNull
	private final String type;

	@NonNull
	private final BigDecimal amount;

	@NonNull
	private final BigDecimal balanceAfterOperation;

	/**
	 * Converts a {@code Operation} to a {@code OperationResponse}
	 * 
	 * @param operation The {@code Operation} to be converted
	 * @return an {@code OperationResponse}
	 */
	public static OperationResponse of(final Operation operation) {
		return new OperationResponse(operation.getDate(),
				operation.getType().name(),
				operation.getAmount().getValue(),
				operation.getBalanceAfterOperation().getValue());
	}

}
