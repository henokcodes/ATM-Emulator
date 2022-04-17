package com.atm.emulator.model;


public class Transaction {

    private long id;
    private String cardNum;
    private String transactionAmount;
    private String transactionType;


    public Transaction() {

    }

    public Transaction(String transactionAmount, String transactionStatus, String transactionType) {
        this.transactionAmount = transactionAmount;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }
    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

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
