package com.example.expense_tracker_oop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class TransactionDAO {

    public static void addTransaction(Transaction transaction) throws IOException, SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            int categoryId = getCategoryID(transaction.getCategoryName());
            int currencyId = getCurrencyID(transaction.getCurrencyName());

            String sql = "INSERT INTO transactions (category_id, amount, type, transaction_date, currency_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, categoryId);
                preparedStatement.setDouble(2, transaction.getAmount());
                preparedStatement.setString(3, transaction.getTransactionType());
                preparedStatement.setDate(4, java.sql.Date.valueOf(transaction.getTransactionDate()));
                preparedStatement.setInt(5, currencyId);

                preparedStatement.executeUpdate();
            }
        }
    }

    private static int getCategoryID(String categoryName) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT id FROM categories WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, categoryName);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        }
        throw new IllegalArgumentException("Category not found: " + categoryName);
    }

    private static int getCurrencyID(String currencyName) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT id FROM currencies WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, currencyName);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        }
        throw new IllegalArgumentException("Currency not found: " + currencyName);
    }
}
