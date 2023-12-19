package com.example.expense_tracker_oop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInController {
    @FXML
    private TextField txtFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Button btnSignUp;

    @FXML
    public void onLogInButtonClicked() {
        // Get user inputs
        String username = txtFieldUsername.getText();
        String password = passwordFieldPassword.getText();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        // Check if the login is valid
        if (isValidLogin(username, password)) {
            try {
                HelloApplication.changeScene("main_page.fxml");
                ExpenseController mainPageController = (ExpenseController) HelloApplication.getLoader().getController();
                mainPageController.setUsername(username);
                double userBalance = getUserBalance(username);
                mainPageController.setBalance(userBalance);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Error loading the main page.");
            }
        } else {
            showAlert("Error", "Invalid username or password. Please try again.");
        }
    }

    public void changeToSignUpPage(ActionEvent event) throws IOException {
        HelloApplication.changeScene("signUp_page.fxml");
    }

    private boolean isValidLogin(String username, String password) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If there is a match, the result set is not empty
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or SQL error
        }
        return false;
    }

    private double getUserBalance(String username) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT initial_balance FROM users WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getDouble("initial_balance");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or SQL error
        }
        return 0.0; // Default balance if not found
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
