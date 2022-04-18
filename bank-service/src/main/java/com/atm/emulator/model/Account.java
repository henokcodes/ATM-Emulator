package com.atm.emulator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

import com.atm.emulator.model.Transaction;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {


    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name="bank_id", nullable=false)
    private Bank bank;

    @OneToOne(mappedBy = "account")
    private Customer customer;

    private String cardNumber;
    private String cardType;
    private double cardBalance;
    private String expireDate;
    private String issueDate;

    @OneToMany(mappedBy="account")
    private Set<Transaction> transactions;


}
