package com.atm.emulator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Account {


    @Id
    @GeneratedValue
    private int id;
    private int bankId;
    private int customerId;
    private String cardNumber;
    private String cardType;
    private double cardBalance;
    private String expireDate;
    private String issueDate;


    public Account() {

    }
    public Account(int bankId, int customerId, String cardNumber, String cardType, double cardBalance, String expireDate, String issueDate) {
        super();
        this.bankId = bankId;
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cardBalance = cardBalance;
        this.expireDate = expireDate;
        this.issueDate = issueDate;


    }
}
