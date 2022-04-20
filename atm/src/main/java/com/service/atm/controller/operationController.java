package com.service.atm.controller;

import com.service.atm.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.service.atm.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping(path = "api/v1/transactions")
public class operationController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody TransactionRequest transactionRequest){
        return this.operationService.withdraw(transactionRequest);
    }
    @PostMapping("/TransactionResponse")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody TransactionRequest transactionRequest){
        return this.operationService.deposit(transactionRequest);
    }
    @PostMapping("/balance")
    public ResponseEntity<QueryResponse> balance(@RequestBody QueryRequest queryRequest){
        return this.operationService.checkBalance(queryRequest);
    }
    @PostMapping("/statement")
    public ResponseEntity<StatementResponse> printStatement(@RequestBody QueryRequest queryRequest){
        return this.operationService.printStatement(queryRequest);
    }
}
