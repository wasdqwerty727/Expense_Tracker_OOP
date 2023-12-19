package com.example.expense_tracker_oop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {
    public static List<Currency> getAllCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM currencies";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String symbol = resultSet.getString("symbol");

                        Currency currency = new Currency(id, name, symbol);
                        currencies.add(currency);
                    }
                }
            }
        }

        return currencies;
    }
}
