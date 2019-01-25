package com.capgemini.account.Account.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.account.Account.entity.Account;
import com.capgemini.account.Account.entity.SavingsAccount;
import com.capgemini.account.Account.repository.AccountRepository;
import com.capgemini.account.Account.service.AccountService;

@RestController
@RequestMapping("/accounts")

public class AccountResource {

	@Autowired
	private AccountService service;
	
	@Autowired
	private AccountRepository repository;

	@PostMapping

	public void addNewAccount(@RequestBody Account account) {
		service.addNewAccount(account);
	}

	@GetMapping

	public List<Account> getAllAccounts() {
		return service.getAllAccounts();
	}

	
	  @GetMapping("/number/{accountNumber}")
	  public Optional<Account> getAccountById(@PathVariable int accountNumber) {
	  return service.getAccountById(accountNumber); }
	 

	@DeleteMapping("/{accountNumber}")

	public void deleteAccount(@PathVariable int accountNumber) {
		service.deleteAccount(accountNumber);
	}

	@GetMapping("/{accountHolderName}")

	public Account getAccountByName(@PathVariable String accountHolderName) {
		return service.getAccountByName(accountHolderName);
	}

	@PutMapping
	public void updateAccount(@RequestBody SavingsAccount account) {
		service.updateAccount(account);
	}
	
	@GetMapping("/{accountNumber}/balance")
	public ResponseEntity<Double> getCurrentBalance(@PathVariable int accountNumber){
		Optional<Account> optional = service.getAccountById(accountNumber);
		double currentBalance = optional.get().getCurrentBalance();
		if(optional.get()==null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(currentBalance,HttpStatus.OK);
	}
	@PutMapping("/{accountNumber}")
	public ResponseEntity<Account> updateAccountBalance(@PathVariable int accountNumber,@RequestParam("currentBalance")Double currentBalance){
		Optional<Account> optional = service.getAccountById(accountNumber);
		Account accounts =  optional.get();
		if(accounts==null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		/* currentBalance+=accounts.getCurrentBalance(); */
		accounts.setCurrentBalance(currentBalance);
		service.updateBalance(accounts);
		return new ResponseEntity<Account>(accounts, HttpStatus.OK);
		
	}
}

