package com.money.transaction.service;

import java.util.List;

import com.money.transaction.model.Account;
import com.money.transaction.model.Transaction;
import com.money.transaction.dto.CreateTransactionRequestDto;
import com.money.transaction.dto.CreateTransactionResponseDto;
import com.money.transaction.dto.TransactionsResponseDto;

public interface TransactionService {
	
	List<Transaction> findAllTransactions();
	
	void save(Transaction transaction);
	
	TransactionsResponseDto listToTransactionsByAccountId(String id);

	TransactionsResponseDto listFromTransactionsByAccountId(String id);
	
	CreateTransactionResponseDto createTransaction(CreateTransactionRequestDto createTransactionRequestDto);
	
	List<Transaction> getToTransactionsByAccount(Account account);
	
	List<Transaction> getFromTransactionsByAccount(Account account);
	
	
}
