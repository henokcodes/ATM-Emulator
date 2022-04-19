package egs.henokcodes.bankservice.services;

import egs.henokcodes.bankservice.controllers.OperationController;
import egs.henokcodes.bankservice.dto.TransactionRequest;
import egs.henokcodes.bankservice.dto.TransactionResponse;
import egs.henokcodes.bankservice.models.Account;
import egs.henokcodes.bankservice.models.Transaction;
import egs.henokcodes.bankservice.repository.AccountRepository;
import egs.henokcodes.bankservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OperationService {

    Logger logger = LoggerFactory.getLogger(OperationService.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private AccountRepository accountRepository;
    private TransactionRequest transactionRequest;


    public ResponseEntity<TransactionResponse> withdraw(String token, TransactionRequest transactionRequest) throws Exception {
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

    public ResponseEntity<TransactionResponse> deposit(String token, TransactionRequest transactionRequest) throws Exception {
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
}
