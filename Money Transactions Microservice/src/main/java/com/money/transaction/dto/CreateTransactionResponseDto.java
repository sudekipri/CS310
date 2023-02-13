package com.money.transaction.dto;

import com.money.transaction.model.Transaction;

public class CreateTransactionResponseDto {

	private String message;
	private Transaction data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Transaction getData() {
		return data;
	}

	public void setData(Transaction data) {
		this.data = data;
	}

	public CreateTransactionResponseDto(String message, Transaction data) {
		this.message = message;
		this.data = data;
	}

}
