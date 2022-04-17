package com.atm.emulator.controller;

import com.atm.emulator.exception.ResourceNotFoundException;
import com.atm.emulator.model.Transaction;
import com.atm.emulator.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("transactions")
    public List<Transaction> getAllTransactions(){
        return this.transactionRepository.findAll();
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable(value = "id") Long transactionId)
            throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + transactionId));
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("/transactions")
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @PutMapping("/transaction/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable(value = "id") Long transactionId,
                                                   @Valid @RequestBody Transaction transactionDetails) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + transactionId));

        transaction.setTransactionAmount(transactionDetails.getTransactionAmount());
        transaction.setTransactionType(transactionDetails.getTransactionType());
        transaction.setTransactionStatus(transactionDetails.getTransactionStatus());
        final Transaction updatedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/transaction/{id}")
    public Map<String, Boolean> deleteTransaction(@PathVariable(value = "id") Long transactionId)
            throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + transactionId));

        transactionRepository.delete(transaction);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
