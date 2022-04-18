package com.atm.emulator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bank {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String address;
    private String code;
    @OneToMany(mappedBy="bank")
    private Set<Account> accounts;

    @OneToMany(mappedBy="bank")
    private Set<ATM> atms;


}
