package com.moneymoney.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.moneymoney.web.entity.CurrentDataSet;
import com.moneymoney.web.entity.Transaction;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@EnableCircuitBreaker
@Controller
public class BankAppController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired

	@RequestMapping("/")
	public String indexForm() {
		return "index";
	}

	@RequestMapping("/withdraw")
	public String withdrawForm() {
		return "WithdrawForm";
	}

	@RequestMapping("/deposit")
	public String depositForm() {
		return "DepositForm";
	}

	@HystrixCommand(defaultFallback = "Failure")
	@RequestMapping("/depositMethod")
	public String deposit(@ModelAttribute Transaction transaction, Model model) {

		restTemplate.postForEntity("http://mmbank/Transaction-service/transactions", transaction, null);
		model.addAttribute("message", "Success!");
		return "DepositForm";

	}

	public String Failure() {
		return "error";
	}

	@HystrixCommand(defaultFallback = "Failure")
	@RequestMapping("/withdrawMethod")
	public String withdraw(@ModelAttribute Transaction transaction, Model model) {
		transaction.setTransactionDetails("Atm");
		restTemplate.postForEntity("http://mmbank/Transaction-service/transactions/withdraw", transaction, null);
		model.addAttribute("message", "Success!");
		return "WithdrawForm";
	}

	@HystrixCommand(defaultFallback = "Failure")
	@RequestMapping("/fundtransfer")
	public String fundTransferForm() {

		return "FundTransfer";
	}

	@HystrixCommand(fallbackMethod = "fundTransferfailure")
	@RequestMapping("/fundTransferForm")
	public String fundTransfer(@RequestParam("senderAccountNumber") int senderAccountNumber,
			@RequestParam("receiverAccountNumber") int receiverAccountNumber, @RequestParam("amount") Double amount,
			@ModelAttribute Transaction transaction, Model model) {
		transaction.setAccountNumber(senderAccountNumber);
		restTemplate.postForEntity("http://mmbank/Transaction-service/transactions/withdraw", transaction, null);
		transaction.setAccountNumber(receiverAccountNumber);
		restTemplate.postForEntity("http://mmbank/Transaction-service/transactions", transaction, null);
		model.addAttribute("message", "Success!");
		return "FundTransfer";

	}

	@HystrixCommand(fallbackMethod = "statementFailure")
	@RequestMapping("/getstatement")
	public ModelAndView getStatementForm(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate
				.getForObject("http://mmbank/Transaction-service/transactions/statement", CurrentDataSet.class);
		int currentSize = size == 0 ? 5 : size;
		int currentOffset = offset == 0 ? 1 : offset;
		Link next = linkTo(methodOn(BankAppController.class).getStatementForm(currentOffset + currentSize, currentSize))
				.withRel("next");
		Link previous = linkTo(
				methodOn(BankAppController.class).getStatementForm(currentOffset - currentSize, currentSize))
						.withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();

		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) {
			if ((transactions.size() <= i && i > 0) || currentOffset < 1)
				break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);

		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		/*
		 * currentDataSet.setNextLink(next); currentDataSet.setPreviousLink(previous);
		 */
		return new ModelAndView("getStatement", "currentDataSet", dataSet);
	}
	public String statementFailure(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		return "error";
	}
	public String fundTransferfailure(@RequestParam("senderAccountNumber") int senderAccountNumber,
			@RequestParam("receiverAccountNumber") int receiverAccountNumber, @RequestParam("amount") Double amount,
			@ModelAttribute Transaction transaction, Model model) {
		model.addAttribute("message", "Wait for sometime!");
		return "FundTransfer";
		
	}
}
