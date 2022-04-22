package egs.henokcodes.bankservice.services;

import egs.henokcodes.bankservice.dto.*;
import egs.henokcodes.bankservice.models.Account;
import egs.henokcodes.bankservice.models.Transaction;
import egs.henokcodes.bankservice.repository.AccountRepository;
import egs.henokcodes.bankservice.repository.TransactionRepository;
import egs.henokcodes.bankservice.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationServiceTest {

    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private MyUserDetailsService userDetailsService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private OperationService operationService;

    @Test
    public void testBalance() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setCardNumber("1234567890123456");
        Account account = new Account();
        account.setCardNumber("1234567890123456");
        account.setCardBalance(1000);
        when(accountRepository.findAccountByCardNumber(queryRequest.getCardNumber())).thenReturn(account);
        when(jwtUtil.validateToken("token", userDetailsService.loadUserByUsername(queryRequest.getCardNumber()))).thenReturn(true);

        ResponseEntity<QueryResponse> responseEntity = operationService.balance("token", queryRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testBalanceBadCredentials() throws Exception {
        String token = "token";
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setCardNumber("1234567890123456");

        when(jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(queryRequest.getCardNumber()))).thenReturn(false);

        ResponseEntity<QueryResponse> responseEntity = operationService.balance(token, queryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testDeposit() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTg1NzYwNzQ5LCJleHAiOjE1ODU3NjQzNDl9.X-_7Zq-8q6Z4_2Xv-y7KVnWYk8p5f4m6_2QrLcRtXvhHp9ZKqywB8uWYV-sD5k6e_2F7f0P3g1xT4CnLmQrXcA";
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setCardNumber("1234567890");
        transactionRequest.setAmount(100);

        Account account = new Account();
        account.setCardNumber("1234567890");
        account.setCardBalance(100);

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus("Successful");
        transaction.setTransactionAmount(100);
        transaction.setTransactionType("DEPOSIT");
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDate.now());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCardNumber("1234567890");
        transactionResponse.setTransactionAmount(100);
        transactionResponse.setTransactionStatus("Successful");
        transactionResponse.setTransactionType("DEPOSIT");
        transactionResponse.setTimestamp(LocalDate.now());

        when(jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(transactionRequest.getCardNumber()))).thenReturn(true);
        when(accountRepository.findAccountByCardNumber(transactionRequest.getCardNumber())).thenReturn(account);

        ResponseEntity<TransactionResponse> responseEntity = operationService.deposit(token, transactionRequest);

        assertEquals(transactionResponse, responseEntity.getBody());

    }

    @Test
    public void testDepositBadCredentials() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setCardNumber("123456789");
        transactionRequest.setAmount(100);
        String token = "123456789";

        when(jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(transactionRequest.getCardNumber()))).thenReturn(false);

        ResponseEntity<TransactionResponse> responseEntity = operationService.deposit(token, transactionRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testStatement() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTg1NzYwNjQ5LCJleHAiOjE1ODU3NjQyNDl9.X-_q-7Z8v4_6Z2XqKWY-7kVnLpf8H2_5Qm6r4cXuRt9ZYyhKvqDVnW-_s1w8pF7BHb6k3e2f0lLg5P4rTmCQcA";
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setCardNumber("1234567890");
        Account account = new Account();
        account.setCardNumber("1234567890");
        account.setCardBalance(1000);
        Transaction transaction = new Transaction();
        transaction.setTransactionStatus("Successful");
        transaction.setTransactionAmount(100);
        transaction.setTransactionType("DEPOSIT");
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDate.now());

        when(userDetailsService.loadUserByUsername(queryRequest.getCardNumber())).thenReturn(account);
        when(jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(queryRequest.getCardNumber()))).thenReturn(true);
        when(accountRepository.findAccountByCardNumber(queryRequest.getCardNumber())).thenReturn(account);
        when(transactionRepository.findTransactionByAccount(account)).thenReturn(transaction);

        ResponseEntity<StatementResponse> responseEntity = operationService.statement(token, queryRequest);

        assertEquals("374245455400126", responseEntity.getBody().getTransactionsList().get(0).getCardNumber());
    }

    @Test
    public void testStatementBadCredentials() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTg1NzQ2MjU5LCJleHAiOjE1ODU3NDYyNjl9.X-_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6_q-7Z8Q4X6";
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setCardNumber("374245455400126");
        when(jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(queryRequest.getCardNumber()))).thenReturn(false);
        ResponseEntity<StatementResponse> response = operationService.statement(token, queryRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testWithdraw() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTg1NzYwNzQ5LCJleHAiOjE1ODU3NjQzNDl9.X-_7Zq8-_7Zq8-_7Zq8-_7Zq8-_7Zq8-_7Zq8-_7Zq8";
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setCardNumber("374245455400126");
        transactionRequest.setAmount(100);

        Account account = new Account();
        account.setCardNumber("1234567890");
        account.setCardBalance(1000);

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus("Successful");
        transaction.setTransactionAmount(100);
        transaction.setTransactionType("WITHDRAW");
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDate.now());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCardNumber("374245455400126");
        transactionResponse.setTransactionAmount(100);
        transactionResponse.setTransactionStatus("Successful");
        transactionResponse.setTransactionType("WITHDRAW");
        transactionResponse.setTimestamp(LocalDate.now());

        when(jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(transactionRequest.getCardNumber()))).thenReturn(true);
        when(accountRepository.findAccountByCardNumber(transactionRequest.getCardNumber())).thenReturn(account);

        ResponseEntity<TransactionResponse> responseEntity = operationService.withdraw(token, transactionRequest);

        assertEquals(transactionResponse, responseEntity);

    }

    @Test
    public void testWithdrawBadCredentials() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setCardNumber("3742454554001");
        transactionRequest.setAmount(100);
        String token = "123456789";

        when(jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(transactionRequest.getCardNumber()))).thenReturn(false);

        ResponseEntity<TransactionResponse> responseEntity = operationService.withdraw(token, transactionRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}


