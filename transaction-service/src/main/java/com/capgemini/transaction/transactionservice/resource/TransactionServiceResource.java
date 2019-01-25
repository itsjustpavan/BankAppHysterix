package com.capgemini.transaction.transactionservice.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.transaction.transactionservice.TransactionService;
import com.capgemini.transaction.transactionservice.entity.Transaction;

@RestController
@RequestMapping("/transactions")
public class TransactionServiceResource {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<Transaction> deposit(@RequestBody Transaction transaction) {
		//System.out.println("Inside Transaction service");
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://Account/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = transactionService.deposit(transaction.getAccountNumber(),
				transaction.getTransactionDetails(), currentBalance, transaction.getAmount());
		restTemplate.put(
				"http://Account/accounts/" + transaction.getAccountNumber() + "?currentBalance=" + updateBalance,
				null);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<Transaction> withdraw(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://Account/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = transactionService.withdraw(transaction.getAccountNumber(),
				transaction.getTransactionDetails(), currentBalance, transaction.getAmount());
		restTemplate.put(
				"http://Account/accounts/" + transaction.getAccountNumber() + "?currentBalance=" + updateBalance,
				null);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/statement")
	public ResponseEntity<CurrentDataSet> getStatement(){
		CurrentDataSet currentDataSet = new CurrentDataSet();
		List<Transaction> transactions = transactionService.getStatement();
		currentDataSet.setTransactions(transactions);
		return new ResponseEntity<CurrentDataSet>(currentDataSet,HttpStatus.OK);
	}
}
