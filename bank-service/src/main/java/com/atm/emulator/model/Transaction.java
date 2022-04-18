package com.atm.emulator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
    @Getter
    @Setter
    @Table(name = "transactions")
    public class Transaction {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        @ManyToOne
        @JoinColumn()
        private Account account;
        private String transactionAmount;
        private String transactionStatus;
        private String transactionType;


        public Transaction() {

        }

        public Transaction(String transactionAmount, String transactionStatus, String transactionType) {
            this.transactionAmount = transactionAmount;
            this.transactionStatus = transactionStatus;
            this.transactionType = transactionType;
        }



        @Override
        public String toString() {
            return "Transaction [id=" + id + ", Transaction Amount = " + transactionAmount + ", Transaction Type = " + transactionType + ", Transaction Status = " + transactionStatus
                    + "]";
        }
}
