package com.ccordier.hexagonalbankaccount.adapters.persistence;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ccordier.hexagonalbankaccount.domain.BankAccount;


/**
 * MongoDB Repository interface to manage Bank Account
 * 
 * @author c.cordier
 */
public interface SpringDataBankAccountRepository extends MongoRepository<BankAccount, UUID> {}
