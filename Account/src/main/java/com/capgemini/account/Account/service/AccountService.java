package com.capgemini.account.Account.service;

import java.util.List;
import java.util.Optional;



import com.capgemini.account.Account.entity.Account;
import com.capgemini.account.Account.entity.SavingsAccount;

public interface AccountService {
	
	void addNewAccount(Account account);
	List<Account> getAllAccounts();
	public Optional<Account> getAccountById(int accountNumber);

	void deleteAccount(int accountNumber);

	
	Account getAccountByName(String accountHolderName);
	 
	void updateAccount(SavingsAccount account);
	void updateBalance(Account accounts);



}
