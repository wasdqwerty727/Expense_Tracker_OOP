package com.example.expense_tracker_oop;

public class Currency {
    private int id;
    private String name;
    private String symbol;

    public Currency(int id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
