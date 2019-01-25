package com.moneymoney.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.moneymoney.web.entity.Transaction;

@Controller
public class BankAppController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/")
	public String indexForm() {
		return "index";
	}
	@RequestMapping("/deposit")
	public String deposit(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://localhost:8989/transactions", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "DepositForm";
	}
	
	@RequestMapping("/with")
	public String withdrawForm() {
		return "WithdrawForm";
	}
	
	@RequestMapping("/withdrawMethod")
	public String withdraw(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://localhost:8989/transactions/withdraw", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "WithdrawForm";
	}
	
	
	
}
	/*@RequestMapping("/fundTransfer")
	public String fundTransferForm()
	{
		return "FundTransfer";
	}
	
	@RequestMapping("/fundTransferForm")
	public String fundTransfer(@RequestParam ("senderAccountNumber") int senderAccountNumber,@RequestParam ("receiverAccountNumber") int receiverAccountNumber,@RequestParam ("amount") Double amount , @ModelAttribute Transaction transaction ,Model model )
	{
	transaction.setAccountNumber("senderAccountNumber")
	restTemplate.postForEntity("http://localhost:8989/transactions", 
			transaction, null);
	transaction.setAccountNumber("receiverAccountNumber")
	restTemplate.postForEntity("http://localhost:8989/transactions", 
			transaction, null);
	model.addAttribute("message","Success!");
	return "FundTransfer";
	}*/
}
