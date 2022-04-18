package com.atm.emulator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
public class Bank {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String address;
    private String code;
    @OneToMany(mappedBy="bank")
    private Set<Account> accounts;

    public Bank() {

    }
    public Bank(String name, String address, String code) {
        super();
        this.name = name;
        this.address = address;
        this.code = code;
    }
}
