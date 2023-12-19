package com.example.expense_tracker_oop;

import java.time.LocalDate;

public class Transaction {
    private String categoryName;
    private String transactionType;
    private String currencyName;
    private double amount;
    private LocalDate transactionDate;

    public Transaction(String categoryName, String transactionType, String currencyName, double amount, LocalDate transactionDate) {
        this.categoryName = categoryName;
        this.transactionType = transactionType;
        this.currencyName = currencyName;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }
}
