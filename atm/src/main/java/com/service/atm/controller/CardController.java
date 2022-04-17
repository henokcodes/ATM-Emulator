package com.service.atm.controller;

import com.service.atm.validator.CardNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController()
@RequestMapping(path = "api/v1/card-number")
public class CardController {

    static RestTemplate restTemplate = new RestTemplate();
    private static final String AUTH_URL = "http://localhost:8080/api/v1/auth";
    private CardNumberValidator cardNumberValidator;
    @PostMapping("/validate")
    public boolean validateCardNumber(@Valid @RequestBody long cardNumber) {
        return cardNumberValidator.isValidCreditCardNumber(cardNumber);
    }

    @PostMapping("/authenticate")
    public void createTransaction(@Valid @RequestBody long cardNumber, @Valid @RequestBody String authType,@Valid @RequestBody String password ) {
        if(authType.equals("PIN")) authenticateWithPin(cardNumber, password);
        if(authType.equals("FINGERPRINT")) authenticateWithFingerPrint(cardNumber, password);
    }

    private void authenticateWithFingerPrint(long cardNumber, String password) {
        ///
    }

    private void authenticateWithPin(long cardNumber, String password) {
        //
        HttpHeaders httpHeaders = new HttpHeaders();
        //    httpHeaders.setAccept(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<String> result =restTemplate.exchange(AUTH_URL, HttpMethod.POST, entity, String.class);
        System.out.println(result);
    }


}
