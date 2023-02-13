package com.money.transaction.dto;

import com.money.transaction.model.Account;

public class CreateAccountResponseDto {

	private String message;
	private Account data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Account getData() {
		return data;
	}

	public void setData(Account data) {
		this.data = data;
	}

	public CreateAccountResponseDto(String message, Account data) {
		this.message = message;
		this.data = data;
	}

	public CreateAccountResponseDto() {
	}

	
}
