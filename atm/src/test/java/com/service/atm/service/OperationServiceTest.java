package com.service.atm.service;

import com.service.atm.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTest {

    private static final String GET_ALL_TRANSACTIONS = "http://localhost:8080/api/v1/transactions/statement";
    private static final String WITHDRAW_TRANSACTION = "http://localhost:8080/api/v1/transactions/withdraw";
    private static final String DEPOSIT_TRANSACTION = "http://localhost:8080/api/v1/transactions/deposit";
    private static final String CHECK_BALANCE = "http://localhost:8080/api/v1/transactions/balance";
    @InjectMocks
    OperationService operationService;

    @Mock
    RestTemplate restTemplate;

    @Test
    public void testWithdraw() {
        TransactionRequest transactionRequest = new TransactionRequest("374245455400126", 100);
        TransactionResponse transactionResponse = new TransactionResponse("374245455400126", "SUCCESS", 100, "WITHDRAW", LocalDate.now());
        ResponseEntity<TransactionResponse> responseEntity = new ResponseEntity<>(transactionResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(WITHDRAW_TRANSACTION, transactionRequest, TransactionResponse.class)).thenReturn(responseEntity);

        ResponseEntity<TransactionResponse> result = operationService.withdraw(transactionRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testWithdrawAmountIsNegative() {
        TransactionRequest transactionRequest = new TransactionRequest("123456789", -100);
        ResponseEntity<TransactionResponse> responseEntity = operationService.withdraw(transactionRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testDeposit() {
        TransactionRequest transactionRequest = new TransactionRequest("374245455400126", 100);
        TransactionResponse transactionResponse = new TransactionResponse("374245455400126", "SUCCESS", 100, "DEPOSIT", LocalDate.now());
        ResponseEntity<TransactionResponse> responseEntity = new ResponseEntity<>(transactionResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(DEPOSIT_TRANSACTION, transactionRequest, TransactionResponse.class)).thenReturn(responseEntity);

        ResponseEntity<TransactionResponse> result = operationService.deposit(transactionRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testDepositWithInvalidAmount() {
        TransactionRequest transactionRequest = new TransactionRequest("123456789", -100);
        ResponseEntity<TransactionResponse> responseEntity = operationService.deposit(transactionRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCheckBalance() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setCardNumber("123456789");
        QueryResponse queryResponse = new QueryResponse();
        queryResponse.setAmount(100);
        queryResponse.setCardNumber("123456789");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + OperationService.authenticationResponse.getJwt());

        HttpEntity<QueryRequest> request = new HttpEntity<>(queryRequest, httpHeaders);

        ResponseEntity<QueryResponse> responseEntity = new ResponseEntity<>(queryResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(CHECK_BALANCE, request, QueryResponse.class)).thenReturn(responseEntity);

        ResponseEntity<QueryResponse> result = operationService.checkBalance(queryRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testCheckBalanceWithInvalidAccountNumber() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setCardNumber("123456789");
        ResponseEntity<QueryResponse> responseEntity = new ResponseEntity<>(new QueryResponse("123456789", 0.0), HttpStatus.OK);
        Mockito.when(restTemplate.postForEntity(CHECK_BALANCE, queryRequest, QueryResponse.class)).thenReturn(responseEntity);
        ResponseEntity<QueryResponse> result = operationService.checkBalance(queryRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testPrintStatement() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setCardNumber("374245455400126");
        StatementResponse statementResponse = new StatementResponse();
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionAmount(100);
        transactionResponse.setCardNumber("374245455400126");
        transactionResponse.setTimestamp( LocalDate.now());
        transactionResponse.setTransactionType("withdraw");
        transactionResponses.add(transactionResponse);
        statementResponse.setTransactionsList(transactionResponses);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + OperationService.authenticationResponse.getJwt());

        HttpEntity<QueryRequest> request = new HttpEntity<>(queryRequest, httpHeaders);

        when(restTemplate.postForEntity(GET_ALL_TRANSACTIONS, request, StatementResponse.class)).thenReturn(new ResponseEntity<>(statementResponse, HttpStatus.OK));

        ResponseEntity<StatementResponse> result = operationService.printStatement(queryRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testPrintStatementWithdraw() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setCardNumber("374245455400126");
        StatementResponse statementResponse = new StatementResponse();
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionAmount(100);
        transactionResponse.setCardNumber("374245455400126");
        transactionResponse.setTransactionType("withdraw");
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        transactionResponses.add(transactionResponse);
        statementResponse.setTransactionsList(transactionResponses);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + OperationService.authenticationResponse.getJwt());

        HttpEntity<QueryRequest> request = new HttpEntity<>(queryRequest, httpHeaders);

        when(restTemplate.postForEntity(GET_ALL_TRANSACTIONS, request, StatementResponse.class)).thenReturn(new ResponseEntity<>(statementResponse, HttpStatus.OK));

        ResponseEntity<StatementResponse> result = operationService.printStatement(queryRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
