package com.money.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.money.transaction.dto.CreateTransactionRequestDto;
import com.money.transaction.dto.CreateTransactionResponseDto;
import com.money.transaction.dto.TransactionsResponseDto;
import com.money.transaction.service.TransactionService;

@RestController
@RequestMapping("/transaction/")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/save")
	public ResponseEntity<CreateTransactionResponseDto> save(@RequestBody CreateTransactionRequestDto request) {
		return new ResponseEntity<>(transactionService.createTransaction(request), HttpStatus.OK);
	}

	@GetMapping("to/{id}")
	public ResponseEntity<TransactionsResponseDto> listToTransactions(@PathVariable("id") String id) {
		return new ResponseEntity<>(transactionService.listToTransactionsByAccountId(id), HttpStatus.OK);
	}

	@GetMapping("from/{id}")
	public ResponseEntity<TransactionsResponseDto> listFromTransactions(@PathVariable("id") String id) {
		return new ResponseEntity<>(transactionService.listFromTransactionsByAccountId(id), HttpStatus.OK);
	}
}
