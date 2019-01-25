package com.capgemini.account.Account;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.capgemini.account.Account.entity.Account;
import com.capgemini.account.Account.entity.SavingsAccount;
import com.capgemini.account.Account.repository.AccountRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}


	@Bean
	public CommandLineRunner init(AccountRepository repository) {

		return (evt) -> {

			repository.save(new SavingsAccount(101,"Pavan",1000.0,true));

			repository.save(new SavingsAccount(102,"Tejas",414.0,false));
			
		};
	}
}

