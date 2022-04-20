package egs.henokcodes.bankservice.services;

import egs.henokcodes.bankservice.controllers.OperationController;
import egs.henokcodes.bankservice.dto.*;
import egs.henokcodes.bankservice.models.Account;
import egs.henokcodes.bankservice.models.Transaction;
import egs.henokcodes.bankservice.repository.AccountRepository;
import egs.henokcodes.bankservice.repository.TransactionRepository;
import egs.henokcodes.bankservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Service
public class OperationService {

    Logger logger = LoggerFactory.getLogger(OperationService.class);

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    private TransactionRequest transactionRequest;


    public ResponseEntity<TransactionResponse> withdraw(String jwt, TransactionRequest transactionRequest) throws Exception {
        String token = "Bearer " + jwt;

        String STATUS;
        if (jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(transactionRequest.getCardNumber()))) {
            String cardNumber = transactionRequest.getCardNumber();
            Account account = this.accountRepository.findAccountByCardNumber(cardNumber);

            if (account.getCardBalance() >= transactionRequest.getAmount()) {
                account.setCardBalance(account.getCardBalance() - transactionRequest.getAmount());
                STATUS = "Successful";
                logger.debug("Successfully Withdrawn");
            } else {
                STATUS = "Incomplete, Insufficient Balance";
                logger.debug("Insufficient Balance");
            }

            Transaction transaction = new Transaction();
            transaction.setTransactionStatus(STATUS);
            transaction.setTransactionAmount(transactionRequest.getAmount());
            transaction.setTransactionType("WITHDRAW");
            transaction.setAccount(account);
            transaction.setTimestamp(LocalDate.now());
            this.transactionRepository.save(transaction);

            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setCardNumber(account.getCardNumber());
            transactionResponse.setTransactionAmount(transactionRequest.getAmount());
            transactionResponse.setTransactionStatus(STATUS);
            transactionResponse.setTransactionType("WITHDRAW");
            transactionResponse.setTimestamp(LocalDate.now());


            return ResponseEntity.ok().body(transactionResponse);
        }
        logger.debug("Authentication Error, Bad credentials ");
        return (ResponseEntity<TransactionResponse>) ResponseEntity.badRequest();
    }

    public ResponseEntity<TransactionResponse> deposit(String jwt, TransactionRequest transactionRequest) throws Exception {
        String token = "Bearer " + jwt;
        String STATUS;
        if (jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(transactionRequest.getCardNumber()))) {
            String cardNumber = transactionRequest.getCardNumber();
            Account account = this.accountRepository.findAccountByCardNumber(cardNumber);
            account.setCardBalance(account.getCardBalance() + transactionRequest.getAmount());
            STATUS = "Successful";
            logger.debug("Successfully Deposited");

            Transaction transaction = new Transaction();
            transaction.setTransactionStatus(STATUS);
            transaction.setTransactionAmount(transactionRequest.getAmount());
            transaction.setTransactionType("DEPOSIT");
            transaction.setAccount(account);
            transaction.setTimestamp(LocalDate.now());
            this.transactionRepository.save(transaction);

            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setCardNumber(account.getCardNumber());
            transactionResponse.setTransactionAmount(transactionRequest.getAmount());
            transactionResponse.setTransactionStatus(STATUS);
            transactionResponse.setTransactionType("DEPOSIT");
            transactionResponse.setTimestamp(LocalDate.now());

            return ResponseEntity.ok().body(transactionResponse);
        }
        logger.debug("Authentication Error, Bad credentials ");

        return (ResponseEntity<TransactionResponse>) ResponseEntity.badRequest();
    }

    public ResponseEntity<QueryResponse> balance(String jwt, QueryRequest queryRequest){
        String token = "Bearer " + jwt;
        if (jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(transactionRequest.getCardNumber()))) {
            logger.debug("Authentication successful");
            String cardNumber = transactionRequest.getCardNumber();
            Account account = this.accountRepository.findAccountByCardNumber(cardNumber);
            QueryResponse response = new QueryResponse();
            response.setCardNumber(cardNumber);
            response.setAmount(account.getCardBalance());
            return ResponseEntity.ok().body(response);
        }
        logger.debug("Authentication error");
        return (ResponseEntity<QueryResponse>) ResponseEntity.badRequest();
    }
    public ResponseEntity<StatementResponse> statement(String jwt, QueryRequest queryRequest){
        String token = "Bearer " + jwt;
        if (jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(queryRequest.getCardNumber()))) {
            logger.debug("Authentication successful");
            Account account = this.accountRepository.findAccountByCardNumber(queryRequest.getCardNumber());
            Transaction transaction = this.transactionRepository.findTransactionByAccount(account);
            StatementResponse statementResponse = new StatementResponse();
            statementResponse.setTransactionsList((List<TransactionResponse>) transaction);
            return ResponseEntity.ok().body(statementResponse);
        }
        logger.debug("Authentication error");
        return (ResponseEntity<StatementResponse>) ResponseEntity.badRequest();
    }


}
