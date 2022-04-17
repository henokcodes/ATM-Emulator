package com.atm.emulator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

    @Entity
    @Table(name = "transactions")
    public class Transaction {

        private long id;
        private String cardNum;
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

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }

        @Column(name = "transaction_amount", nullable = false)
        public String getTransactionAmount() {
            return transactionAmount;
        }
        public void setTransactionAmount(String transactionAmount) {
            this.transactionAmount = transactionAmount;
        }

        @Column(name = "transaction_status", nullable = false)
        public String getTransactionStatus() {
            return transactionStatus;
        }
        public void setTransactionStatus(String transactionStatus) {
            this.transactionStatus = transactionStatus;
        }

        @Column(name = "transaction_type", nullable = false)
        public String getTransactionType() {
            return transactionType;
        }
        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        @Override
        public String toString() {
            return "Transaction [id=" + id + ", Transaction Amount = " + transactionAmount + ", Transaction Type = " + transactionType + ", Transaction Status = " + transactionStatus
                    + "]";
        }
}
