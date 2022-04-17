package com.atm.emulator.repository;

import com.atm.emulator.model.Account;
import com.atm.emulator.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}