package com.atm.emulator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String address;
    private String phone;
    @OneToOne(mappedBy = "customer")
    private Account account;

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + "]";
    }

}
