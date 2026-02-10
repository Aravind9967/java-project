package com.BankManager.union.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankManager.union.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {

	
}
