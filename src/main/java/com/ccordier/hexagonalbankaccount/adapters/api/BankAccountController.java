package com.ccordier.hexagonalbankaccount.adapters.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccordier.hexagonalbankaccount.adapters.api.response.OperationResponse;
import com.ccordier.hexagonalbankaccount.port.incoming.DepositUseCase;
import com.ccordier.hexagonalbankaccount.port.incoming.HistoryUseCase;
import com.ccordier.hexagonalbankaccount.port.incoming.WithdrawUseCase;


/**
 * REST Controller Api to manage Bank Account
 * 
 * @author c.cordier
 */
@RestController
@RequestMapping("v1.0/accounts")
public class BankAccountController {

	private final DepositUseCase depositUseCase;
	private final WithdrawUseCase withdrawUseCase;
	private final HistoryUseCase historyUseCase;

	public BankAccountController(final DepositUseCase depositUseCase,
			final WithdrawUseCase withdrawUseCase,
			final HistoryUseCase historyUseCase) {
		this.depositUseCase = depositUseCase;
		this.withdrawUseCase = withdrawUseCase;
		this.historyUseCase = historyUseCase;
	}

	/**
	 * Make a deposit in the bank account
	 * 
	 * @param id identifier of the bank account
	 * @param amount The amount to deposit
	 */
	@PostMapping(value = "/{id}/deposits/{amount}")
	public void deposit(@PathVariable final UUID id, @PathVariable final BigDecimal amount) {
		depositUseCase.deposit(id, amount);
	}

	/**
	 *
	 * Make a withdrawal from the bank account
	 * 
	 * @param id identifier of the bank account
	 * @param amount The amout to withdraw
	 */
	@PostMapping(value = "/{id}/withdraws/{amount}")
	public void withdraw(@PathVariable final UUID id, @PathVariable final BigDecimal amount) {
		withdrawUseCase.withdraw(id, amount);
	}

	/**
	 * Returns the history of operations on the bank account
	 * 
	 * @param id identifier of the bank account
	 * @return a list of {@code OperationResponse} ordered by date
	 */
	@GetMapping(value = "/{id}/history")
	public List<OperationResponse> history(@PathVariable final UUID id) {
		return historyUseCase.history(id)
				.stream()
				.map(operation -> OperationResponse.of(operation))
				.collect(Collectors.toUnmodifiableList());
	}

}
