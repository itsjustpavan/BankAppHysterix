package com.capgemini.account.Account.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.account.Account.entity.Account;


@Repository
public interface AccountRepository extends MongoRepository<Account, Integer>{

	 Account getAccountByaccountHolderName(String accountHolderName); 
}
