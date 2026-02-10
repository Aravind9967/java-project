package com.BankManager.union.serice;

import java.util.List;


import com.BankManager.union.entity.Account;

public interface AccountService {
	
	public Account createAccount(Account account);
	public Account getAccountDetailsByAccountNumber(Long accountNumber);
    public List<Account> getAllAcccountDetails();
    public Account depositAmount(Long accountNumber, Double amount);
    public Account withdrawAmount(Long AccountNumber, Double amount);
    public void closeAccount(Long accountNumber);
    
    
}
