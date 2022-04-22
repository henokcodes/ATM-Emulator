package com.service.atm.service;


import com.service.atm.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Slf4j
@Service
public class OperationService {

    //all constants will be in an external file
    private static final String GET_ALL_TRANSACTIONS = "http://localhost:8080/api/v1/transactions/statement";
    private static final String WITHDRAW_TRANSACTION = "http://localhost:8080/api/v1/transactions/withdraw";
    private static final String DEPOSIT_TRANSACTION = "http://localhost:8080/api/v1/transactions/deposit";
    private static final String CHECK_BALANCE = "http://localhost:8080/api/v1/transactions/balance";

    static RestTemplate restTemplate = new RestTemplate();
    static AuthenticationResponse authenticationResponse;
    static HttpHeaders httpHeaders = new HttpHeaders();

    public static ResponseEntity<TransactionResponse> withdraw(TransactionRequest transactionRequest){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + authenticationResponse.getJwt());
        // build the request
        HttpEntity<TransactionRequest> request = new HttpEntity<>(transactionRequest, httpHeaders);
        ResponseEntity<TransactionResponse> result =restTemplate.postForEntity(WITHDRAW_TRANSACTION, request, TransactionResponse.class);
        log.debug("POST request to withdraw amount returned with: " + String.valueOf(result));

       return  result;
    }
    public ResponseEntity<TransactionResponse> deposit(TransactionRequest transactionRequest){

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + authenticationResponse.getJwt());
        // build the request
        HttpEntity<TransactionRequest> request = new HttpEntity<>(transactionRequest, httpHeaders);
        ResponseEntity<TransactionResponse> result =restTemplate.postForEntity(DEPOSIT_TRANSACTION, request, TransactionResponse.class);
        log.debug("POST request to deposit amount returned with: " + String.valueOf(result));

        return result;
    }
    public static ResponseEntity<QueryResponse> checkBalance(QueryRequest queryRequest){

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + authenticationResponse.getJwt());
        // build the request
        HttpEntity<QueryRequest> request = new HttpEntity<>(queryRequest, httpHeaders);
        ResponseEntity<QueryResponse> result =restTemplate.postForEntity(CHECK_BALANCE, request, QueryResponse.class);
        log.debug("POST request to check balance returned with: " + String.valueOf(result));

        return  result;
    }
    public static ResponseEntity<StatementResponse> printStatement(QueryRequest queryRequest){

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + authenticationResponse.getJwt());
        // build the request
        HttpEntity<QueryRequest> request = new HttpEntity<>(queryRequest, httpHeaders);
        ResponseEntity<StatementResponse> result =restTemplate.postForEntity(GET_ALL_TRANSACTIONS, request, StatementResponse.class);
        log.debug("POST request to print statements returned with: " + String.valueOf(result));

        return result;
    }

}

