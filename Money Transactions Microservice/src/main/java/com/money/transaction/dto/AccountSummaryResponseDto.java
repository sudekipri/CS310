package com.money.transaction.dto;

public class AccountSummaryResponseDto {
	private String message;
	private AccountSummaryDataDto data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AccountSummaryDataDto getData() {
		return data;
	}

	public void setData(AccountSummaryDataDto data) {
		this.data = data;
	}

	public AccountSummaryResponseDto(String message, AccountSummaryDataDto data) {
		this.message = message;
		this.data = data;
	}

}
