package com.BankManager.union.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankManager.union.entity.Account;
import com.BankManager.union.serice.AccountService;


@RestController
@RequestMapping("/account")
public class Controller {
	@Autowired
	AccountService  accountService;
	
	@PostMapping("/create")
	public Account createAccount(@RequestBody Account account) {
		
	return accountService.createAccount(account);
		

	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable Long accountNumber) {
		
	
	    Account account = accountService.getAccountDetailsByAccountNumber(accountNumber);
	    return ResponseEntity.ok(account);

}
	
	@GetMapping("/getallaccounts")
	public ResponseEntity<List<Account>> getAllAccountDetails(){
		List<Account> allAccountDetails = accountService.getAllAcccountDetails();
		return ResponseEntity.ok(allAccountDetails);
	}
	
	@PutMapping("/deposit/{accountNumber}/{amount}")
	public Account depositAccount(@PathVariable Long accountNumber, @PathVariable Double amount) {
		Account account = accountService.depositAmount(accountNumber, amount);
		return account;
		
	}
	
	@PutMapping("/withdraw/{accountNumber}/{amount}")
	public Account withdrawAccount(@PathVariable Long accountNumber, @PathVariable Double amount) {
		Account account = accountService.withdrawAmount(accountNumber, amount);
		return account;
	}
	
	@DeleteMapping("/delete/{accountNumber}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long accountNumber) {
		accountService.closeAccount(accountNumber);
		return ResponseEntity.ok("Account is closed");

	}
	
}
