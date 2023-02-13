package com.money.transaction.service;

import com.money.transaction.model.Account;

import java.util.List;

import com.money.transaction.dto.AccountSummaryResponseDto;
import com.money.transaction.dto.CreateAccountResponseDto;

public interface AccountService {
	
	List<Account> findAllAccounts();
	
	Account findAccountById(String id);
	
	Account save(Account account);

	CreateAccountResponseDto createAccount(Account account);
	
	AccountSummaryResponseDto listAccountSummaryByAccountId(String id);

}
