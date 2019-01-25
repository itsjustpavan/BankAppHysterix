package com.capgemini.transaction.transactionservice;

import java.util.List;

import com.capgemini.transaction.transactionservice.entity.Transaction;

public interface TransactionService {
	Double withdraw(int accountNumber, String transactioDetails, double currentBalance, double amount);

	Double deposit(int accountNumber, String transactioDetails, double currentBalance, double amount);

	List<Transaction> getStatement();


}
