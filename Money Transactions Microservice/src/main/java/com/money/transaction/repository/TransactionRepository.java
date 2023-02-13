package com.money.transaction.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.money.transaction.model.Account;
import com.money.transaction.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

	List<Transaction> findAllByFrom(Account account);

	List<Transaction> findAllByTo(Account account);

}
