package com.example.expense_tracker_oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    static String url = "jdbc:postgresql:expense_tracker";
    static String username = "postgres";
    static String password = "databasesql";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
