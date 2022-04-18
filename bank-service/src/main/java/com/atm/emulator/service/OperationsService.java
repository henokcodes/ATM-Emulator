package com.atm.emulator.service;

import com.atm.emulator.dto.operationRequest;
import com.atm.emulator.exception.ResourceNotFoundException;
import com.atm.emulator.model.Account;
import com.atm.emulator.model.Transaction;
import com.atm.emulator.repository.AccountRepository;
import com.atm.emulator.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
public class OperationsService {

    Logger logger = LoggerFactory.getLogger(OperationsService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<Transaction> getTransactionsByAccount( Account account)
            throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findById(account)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this Account ID :: " + transactionId));
        return ResponseEntity.ok().body(transaction);
    }

    public ResponseEntity<Transaction> withdraw(@Valid @RequestBody operationRequest req) {
        logger.debug("Withdraw operation");
         String STATUS;
        Account account = this.accountRepository.findOne(req);
        if(account.getCardBalance() >= req.amount){
            account.setCardBalance(account.getCardBalance() - amount);
            STATUS = "Successful";
            logger.debug("Successfully Withdrawn");
        }else{
            STATUS = "Incomplete";
            logger.debug("Insufficient Balance");
        }
        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(STATUS);
        transaction.setTransactionAmount(amount);
        transaction.setTransactionType("Withdraw");
        transaction.setAccount(account);


        return ResponseEntity.ok().body(transaction);
    }

    public ResponseEntity<Transaction> deposit(@Valid @RequestBody operationRequest req) {
          logger.debug("Deposit operation");

        String STATUS;
        Account account = this.accountRepository.findOne(req);
            account.setCardBalance(account.getCardBalance() + amount);
            STATUS = "Successful";
        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(STATUS);
        transaction.setTransactionAmount(amount);
        transaction.setTransactionType("Deposit");
        transaction.setAccount(account);


        return ResponseEntity.ok().body(transaction);
    }

    public double balance(@Valid @RequestBody operationRequest req) {
          logger.debug("check balance");

        Account account = this.accountRepository.findOne(req);
        return account.getCardBalance();
    }
}
