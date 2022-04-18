package com.atm.emulator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String address;
    private String phone;

    public Customer() {

    }
    public Customer(String name, String address, String phone) {
        super();
        this.name = name;
        this.address = address;
        this.phone = phone;
    }



    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + "]";
    }

}
