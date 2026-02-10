package com.BankManager.union.serice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankManager.union.Repository.AccountRepository;
import com.BankManager.union.entity.Account;

@Service
public class AccountServiceImpl implements AccountService{
@Autowired
	AccountRepository repo;
	
	@Override
	public Account createAccount(Account account) {
		
	 Account accountsaved =	repo.save(account);
		return accountsaved;
	}

	@Override
	public Account getAccountDetailsByAccountNumber(Long accountNumber) {
	   
	Optional<Account> account =	repo.findById(accountNumber);
	if(account.isEmpty()) {
		throw new RuntimeException("Account is not found");
		
	}
	Account accountfound = account.get();
		return accountfound;
	}

	@Override
	public List<Account> getAllAcccountDetails() {
	List<Account> listofAccounts = repo.findAll();
		return listofAccounts;
	}

	@Override
	public Account depositAmount(Long accountNumber, Double amount) {
		 Optional<Account> account = repo.findById(accountNumber);
	     if(account.isEmpty()) {
	    	 throw new RuntimeException("Account is not prisent");
	    	 
	     }
	     Account accountPresent = account.get();
	      Double totalBalance = accountPresent.getAccountBalance()+amount;
	      accountPresent.setAccountBalance(totalBalance);
	      repo.save(accountPresent);
			return accountPresent;
	}

	@Override
	public Account withdrawAmount(Long accountNumber, Double amount) {
     Optional<Account> account = repo.findById(accountNumber);
     if(account.isEmpty()) {
    	 throw new RuntimeException("Account is not prisent");
    	 
     }
     
      Account accountPresent = account.get();
      Double totalBalance = accountPresent.getAccountBalance()-amount;
      accountPresent.setAccountBalance(totalBalance);
      repo.save(accountPresent);
		return accountPresent;
	}

	@Override
	public void closeAccount(Long accountNumber) {
		getAccountDetailsByAccountNumber(accountNumber);
		
		repo.deleteById(accountNumber);
		
	}



}
