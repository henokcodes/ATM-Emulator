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

    @GetMapping("transactions")
    public List<Transaction> printTransactions(){
        return this.operationsService.printTransactions();
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable(value = "id") Long accountId)
            throws ResourceNotFoundException {
      return this.operationsService.getTransactionById(accountId);
    }

//    @PostMapping("/transactions")
//    public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {
//        return transactionRepository.save(transaction);
//    }
    @PostMapping("/transactions/withdraw")
    public ResponseEntity<Transaction> withdraw(@Valid @RequestBody operationRequest req) {
        return operationsService.withdraw(req);
    }


//    @PutMapping("/transaction/{id}")
//    public ResponseEntity<Transaction> updateTransaction(@PathVariable(value = "id") Long transactionId,
//                                                   @Valid @RequestBody Transaction transactionDetails) throws ResourceNotFoundException {
//        Transaction transaction = transactionRepository.findById(transactionId)
//                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + transactionId));
//
//        transaction.setTransactionAmount(transactionDetails.getTransactionAmount());
//        transaction.setTransactionType(transactionDetails.getTransactionType());
//        transaction.setTransactionStatus(transactionDetails.getTransactionStatus());
//        final Transaction updatedTransaction = transactionRepository.save(transaction);
//        return ResponseEntity.ok(updatedTransaction);
//    }
//
//    @DeleteMapping("/transaction/{id}")
//    public Map<String, Boolean> deleteTransaction(@PathVariable(value = "id") Long transactionId)
//            throws ResourceNotFoundException {
//        Transaction transaction = transactionRepository.findById(transactionId)
//                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + transactionId));
//
//        transactionRepository.delete(transaction);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return response;
//    }
}
