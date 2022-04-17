package com.service.atm.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.atm.emulator.model.Transaction;

@Service
public class operationService {
    private static final String GET_ALL_TRANSACTIONS = "http://localhost:8080/api/v1/transactions";
    private static final String WITHDRAW_TRANSACTION = "http://localhost:8080/api/v1/transactions";
    private static final String DEPOSIT_TRANSACTION = "http://localhost:8080/api/v1/transactions";
    private static final String CHECK_BALANCE = "http://localhost:8080/api/v1/transactions";

    static RestTemplate restTemplate = new RestTemplate();

//    public static ResponseEntity<String> getTransactions(){
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        //    httpHeaders.setAccept(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
//
//        ResponseEntity<String> result =restTemplate.exchange(GET_ALL_TRANSACTIONS, HttpMethod.GET, entity, String.class);
//        System.out.println(result);
//        return result;
//    }

    public static ResponseEntity<String> withdraw(double amount){
        HttpHeaders httpHeaders = new HttpHeaders();
        //    httpHeaders.setAccept(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<String> result =restTemplate.exchange(GET_ALL_TRANSACTIONS, HttpMethod.POST, entity, String.class);
        System.out.println(result);

        return  result;
    }
    public static ResponseEntity<String> deposit(double amount){

        HttpHeaders httpHeaders = new HttpHeaders();
        //    httpHeaders.setAccept(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<String> result =restTemplate.exchange(GET_ALL_TRANSACTIONS, HttpMethod.POST, entity, String.class);
        System.out.println(result);

        return result;
    }
    public static ResponseEntity<String> checkBalance(){

        HttpHeaders httpHeaders = new HttpHeaders();
        //    httpHeaders.setAccept(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<String> result =restTemplate.exchange(GET_ALL_TRANSACTIONS, HttpMethod.POST, entity, String.class);
        System.out.println(result);

        return  result;
    }
    public static ResponseEntity<String> printStatement(){

        HttpHeaders httpHeaders = new HttpHeaders();
        //    httpHeaders.setAccept(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<String> result =restTemplate.exchange(GET_ALL_TRANSACTIONS, HttpMethod.POST, entity, String.class);
        System.out.println(result);

        return result;
    }
}

