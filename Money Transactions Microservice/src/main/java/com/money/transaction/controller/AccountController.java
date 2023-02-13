package com.money.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.money.transaction.dto.AccountSummaryResponseDto;
import com.money.transaction.dto.CreateAccountResponseDto;
import com.money.transaction.model.Account;
import com.money.transaction.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/save")
	public ResponseEntity<CreateAccountResponseDto> create(@RequestBody Account account) {
		return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountSummaryResponseDto> listAccountSummary(@PathVariable String id) {
		return new ResponseEntity<>(accountService.listAccountSummaryByAccountId(id), HttpStatus.OK);
	}

}
