package com.money.transaction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.money.transaction.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {

}
