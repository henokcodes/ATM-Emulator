package com.atm.emulator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "transactions")
    public class Transaction {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        @ManyToOne
        @JoinColumn(name="account_id", nullable=false)
        private Account account;
        private String transactionAmount;
        private String transactionStatus;
        private String transactionType;

        @Override
        public String toString() {
            return "Transaction [id=" + id + ", Transaction Amount = " + transactionAmount + ", Transaction Type = " + transactionType + ", Transaction Status = " + transactionStatus
                    + "]";
        }
}
