package com.service.atm.controller;

import com.service.atm.dto.AuthenticationRequest;
import com.service.atm.dto.AuthenticationResponse;
import com.service.atm.dto.TransactionRequest;
import com.service.atm.dto.TransactionResponse;
import com.service.atm.validator.CardNumberValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/card")
public class CardController {



    Logger logger = LoggerFactory.getLogger(CardController.class);
    static RestTemplate restTemplate = new RestTemplate();
    private static final String PIN_AUTH_URL = "http://localhost:8080/api/v1/transactions/authenticate"; // will put it in a constants class
//    private static final String FINGERPRINT_AUTH_URL = "http://localhost:8080/api/v1/auth"; // will put it in a constants class

    private CardNumberValidator cardNumberValidator;
    @PostMapping("/validate")
    public boolean validateCardNumber(@Valid @RequestBody long cardNumber) {
        return cardNumberValidator.isValidCreditCardNumber(cardNumber);
    }


    @PostMapping("/authenticate")
    public void createTransaction(@Valid @RequestBody String authType, @Valid @RequestBody AuthenticationRequest authenticationRequest ) {
        if(authType.equals("PIN")) authenticateWithPin(authenticationRequest);
//        else if(authType.equals("FINGERPRINT")) authenticateWithFingerPrint(cardNumber, password);
    }

//    private ResponseEntity<String> authenticateWithFingerPrint(String cardNumber, String password) {
//        HttpHeaders httpHeaders = new HttpHeaders();
//           httpHeaders.setAccept((List<MediaType>) MediaType.APPLICATION_JSON);
//            httpHeaders.put("username", Collections.singletonList(cardNumber));
//        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
//
//        ResponseEntity<String> result =restTemplate.exchange(PIN_AUTH_URL, HttpMethod.POST, entity, String.class);
//
//        logger.debug(String.valueOf(result));
//        return  result;
//    }

    private ResponseEntity<AuthenticationResponse> authenticateWithPin(AuthenticationRequest authenticationRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);// build the request
        HttpEntity<AuthenticationRequest> request = new HttpEntity<>(authenticationRequest, httpHeaders);
        ResponseEntity<AuthenticationResponse> result =restTemplate.postForEntity(PIN_AUTH_URL, request, AuthenticationResponse.class);
        logger.debug("Authentication with PIN returned with: " + String.valueOf(result));
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwt(result.getBody().getJwt());
        return result;
    }


}
