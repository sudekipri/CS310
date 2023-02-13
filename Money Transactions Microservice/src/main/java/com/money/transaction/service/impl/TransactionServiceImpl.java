package com.money.transaction.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.money.transaction.constants.MessageConstant;
import com.money.transaction.dto.CreateTransactionRequestDto;
import com.money.transaction.dto.CreateTransactionResponseDto;
import com.money.transaction.dto.TransactionsResponseDto;
import com.money.transaction.model.Account;
import com.money.transaction.model.Transaction;
import com.money.transaction.repository.TransactionRepository;
import com.money.transaction.service.AccountService;
import com.money.transaction.service.TransactionService;
import com.money.transaction.util.DateUtil;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final AccountService accountService;

	public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
		this.transactionRepository = transactionRepository;
		this.accountService = accountService;
	}

	@Override
	public List<Transaction> findAllTransactions() {
		return transactionRepository.findAll();
	}

	@Override
	public void save(Transaction transaction) {
		transactionRepository.save(transaction);
	}

	@SuppressWarnings("deprecation")
	@Override
	public CreateTransactionResponseDto createTransaction(CreateTransactionRequestDto request) {
		if (StringUtils.isEmpty(request.getToAccountId()) || StringUtils.isEmpty(request.getFromAccountId())
				|| request.getAmount() == null) {
			return new CreateTransactionResponseDto(MessageConstant.ERROR_MISSING_FIELDS, null);

		}

		Account toAccount = accountService.findAccountById(request.getToAccountId());
		Account fromAccount = accountService.findAccountById(request.getFromAccountId());

		if (Objects.isNull(fromAccount) || Objects.isNull(toAccount)) {
			return new CreateTransactionResponseDto(MessageConstant.ERROR_ACCOUNT_ID, null);

		}

		else {
			Transaction transaction = new Transaction(fromAccount, toAccount, DateUtil.getCurrentDate(),
					request.getAmount());
			transaction = transactionRepository.save(transaction);
			return new CreateTransactionResponseDto(MessageConstant.SUCCESS, transaction);
		}

	}

	@Override
	public TransactionsResponseDto listToTransactionsByAccountId(String id) {
		Account account = accountService.findAccountById(id);

		if (Objects.isNull(account)) {
			return new TransactionsResponseDto(MessageConstant.ERROR_ACCOUNT_NO_EXIST, null);
		}

		List<Transaction> toTransactions = getToTransactionsByAccount(account);
		return new TransactionsResponseDto(MessageConstant.SUCCESS, toTransactions);
	}

	@Override
	public TransactionsResponseDto listFromTransactionsByAccountId(String id) {
		Account account = accountService.findAccountById(id);

		if (Objects.isNull(account)) {
			return new TransactionsResponseDto(MessageConstant.ERROR_ACCOUNT_NO_EXIST, null);
		}

		List<Transaction> fromTransactions = getFromTransactionsByAccount(account);

		return new TransactionsResponseDto(MessageConstant.SUCCESS, fromTransactions);
	}

	@Override
	public List<Transaction> getToTransactionsByAccount(Account account) {
		return transactionRepository.findAllByTo(account);
	}

	@Override
	public List<Transaction> getFromTransactionsByAccount(Account account) {
		return transactionRepository.findAllByFrom(account);
	}

}
