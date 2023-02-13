package com.money.transaction.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.money.transaction.constants.MessageConstant;
import com.money.transaction.dto.AccountSummaryDataDto;
import com.money.transaction.dto.AccountSummaryResponseDto;
import com.money.transaction.dto.CreateAccountResponseDto;
import com.money.transaction.model.Account;
import com.money.transaction.model.Transaction;
import com.money.transaction.repository.AccountRepository;
import com.money.transaction.repository.TransactionRepository;
import com.money.transaction.service.AccountService;
import com.money.transaction.util.DateUtil;

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;

	public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public List<Account> findAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public CreateAccountResponseDto createAccount(Account account) {

		if (StringUtils.isEmpty(account.getId()) || StringUtils.isEmpty(account.getOwner())) {
			return new CreateAccountResponseDto(MessageConstant.ERROR_MISSING_FIELDS, null);
		}

		account.setCreateDate(DateUtil.getCurrentDate());
		Account savedAccount = accountRepository.save(account);
		return new CreateAccountResponseDto(MessageConstant.SUCCESS, savedAccount);
	}

	@Override
	public Account findAccountById(String id) {
		return accountRepository.findById(id).orElse(null);
	}

	@Override
	public AccountSummaryResponseDto listAccountSummaryByAccountId(String id) {
		Account account = findAccountById(id);
		if (account == null) {
			return new AccountSummaryResponseDto(MessageConstant.ERROR_ACCOUNT_NO_EXIST, null);
		}

		List<Transaction> toTrancastions = transactionRepository.findAllByTo(account);
		List<Transaction> fromTrancastions = transactionRepository.findAllByFrom(account);

		AccountSummaryDataDto data = new AccountSummaryDataDto();
		data.setTransactionsIn(toTrancastions);
		data.setTransactionsOut(fromTrancastions);
		data.setId(account.getId());
		data.setOwner(account.getOwner());
		data.setCreateDate(account.getCreateDate());

		data.setBalance(calculateBalance(toTrancastions, fromTrancastions));
		return new AccountSummaryResponseDto(MessageConstant.SUCCESS, data);
	}

	private Double calculateBalance(List<Transaction> toTrancastions, List<Transaction> fromTrancastions) {
		Double balance = 0D;
		for (Transaction transaction : toTrancastions) {
			balance += transaction.getAmount();
		}
		for (Transaction transaction : fromTrancastions) {
			balance -= transaction.getAmount();
		}
		return balance;
	}

}
