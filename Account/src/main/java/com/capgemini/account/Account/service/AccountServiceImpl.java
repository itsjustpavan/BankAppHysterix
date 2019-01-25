package com.capgemini.account.Account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.account.Account.entity.Account;
import com.capgemini.account.Account.entity.SavingsAccount;
import com.capgemini.account.Account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repository;

	@Override
	public void addNewAccount(Account account) {
		// TODO Auto-generated method stub
		repository.save(account);

	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Optional<Account> getAccountById(int accountNumber) {
		// TODO Auto-generated method stub
		return repository.findById(accountNumber);
	}

	@Override
	public void deleteAccount(int accountNumber) {
		// TODO Auto-generated method stub
		repository.deleteById(accountNumber);
	}

	@Override
	public Account getAccountByName(String accountHolderName) {
		return repository.getAccountByaccountHolderName(accountHolderName);

	}

	@Override
	public void updateAccount(SavingsAccount account) {
		// TODO Auto-generated method stub
		repository.save(account);

	}

	@Override
	public void updateBalance(Account accounts) {
		repository.save(accounts);
		
	}

	
}
