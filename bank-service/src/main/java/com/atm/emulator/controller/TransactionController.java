package com.atm.emulator.controller;

import com.atm.emulator.exception.ResourceNotFoundException;
import com.atm.emulator.model.Transaction;
import com.atm.emulator.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.atm.emulator.dto.operationRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class TransactionController {


    @Autowired
    private OperationsService operationsService;

    @PostMapping("/transactions/withdraw")
    public ResponseEntity<Transaction> withdraw(@Valid @RequestHeader("Authorization") String token, @RequestBody String cardNumber, @RequestBody double amount) {
        return operationsService.withdraw(token, cardNumber, amount);
    }

    @PostMapping("/transactions/deposit")
    public ResponseEntity<Transaction> deposit(@RequestHeader("Authorization") String token, @RequestBody String cardNumber, @RequestBody double amount) {
        return operationsService.deposit(token, cardNumber, amount);
    }
    @PostMapping("/transactions/balance")
    public Double balance(@RequestHeader("Authorization") String token, @RequestBody String cardNumber) {
        return operationsService.balance(token, cardNumber);
    }
    @PostMapping("/transactions/statement")
    public List<Transaction> printTransactions() throws ResourceNotFoundException {
        return this.operationsService.getTransactionsByAccount();
    }


}
