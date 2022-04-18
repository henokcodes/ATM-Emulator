package com.atm.emulator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

import com.atm.emulator.model.Transaction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {


    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name="bank_id", nullable=false)
    private Bank bank;

    @OneToOne(mappedBy = "account")
    private Customer customer;

    private String cardNumber;
    private double cardBalance;
    private String expireDate;
    private String issueDate;

    @OneToMany(mappedBy="account")
    private Set<Transaction> transactions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.cardNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        //more than 3 attempts, account will be locked
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
