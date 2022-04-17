package com.atm.emulator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ATM {

    @Id
    @GeneratedValue
    private int id;
    private int bankId;
    private String name;
    private String address;
    private String branch;


    public ATM() {

    }
    public ATM(int bankId, String name, String address, String branch) {
        super();
        this.bankId = bankId;
        this.name = name;
        this.address = address;
        this.branch = branch;
    }
}
