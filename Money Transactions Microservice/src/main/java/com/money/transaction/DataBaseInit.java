package com.money.transaction;

import org.springframework.stereotype.Component;

import com.money.transaction.model.Account;
import com.money.transaction.model.Transaction;
import com.money.transaction.service.AccountService;
import com.money.transaction.service.TransactionService;
import com.money.transaction.util.DateUtil;

@Component
public class DataBaseInit {

	private final AccountService accountService;
	private final TransactionService transactionService;

	public DataBaseInit(AccountService accountService, TransactionService transactionService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
		initDataBase();
	}

	private void initDataBase() {
		if (transactionService.findAllTransactions().isEmpty() && accountService.findAllAccounts().isEmpty()) {
			String createDate = DateUtil.getCurrentDate();

			Account account1 = accountService.save(new Account("1111", "Henry Williams", createDate));
			Account account2 = accountService.save(new Account("2222", "Jack Johns", createDate));

			transactionService.save(new Transaction(account1, account2, createDate, 1500D));
			transactionService.save(new Transaction(account2, account1, createDate, 2500D));
		}
	}
}