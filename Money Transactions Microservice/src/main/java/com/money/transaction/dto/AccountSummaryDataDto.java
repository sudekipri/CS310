package com.money.transaction.dto;

import java.util.List;

import com.money.transaction.model.Transaction;

public class AccountSummaryDataDto {
	private String id;

	private String owner;

	private String createDate;

	private List<Transaction> transactionsOut;

	private List<Transaction> transactionsIn;

	private Double balance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public List<Transaction> getTransactionsOut() {
		return transactionsOut;
	}

	public void setTransactionsOut(List<Transaction> transactionsOut) {
		this.transactionsOut = transactionsOut;
	}

	public List<Transaction> getTransactionsIn() {
		return transactionsIn;
	}

	public void setTransactionsIn(List<Transaction> transactionsIn) {
		this.transactionsIn = transactionsIn;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}
