package com.example.expense_tracker_oop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public static List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM categories";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");

                        Category category = new Category(id, name);
                        categories.add(category);
                    }
                }
            }
        }

        return categories;
    }
}
