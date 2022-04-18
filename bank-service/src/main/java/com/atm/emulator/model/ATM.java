package com.atm.emulator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ATM {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String address;
    private String branch;

    @ManyToOne
    @JoinColumn(name="bank_id", nullable=false)
    private Bank bank;


}
