package com.money.transaction.dto;

import java.util.List;

import com.money.transaction.model.Transaction;

public class TransactionsResponseDto {

	private String message;
	private List<Transaction> data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Transaction> getData() {
		return data;
	}

	public void setData(List<Transaction> data) {
		this.data = data;
	}

	public TransactionsResponseDto(String message, List<Transaction> data) {
		this.message = message;
		this.data = data;
	}

	
}
