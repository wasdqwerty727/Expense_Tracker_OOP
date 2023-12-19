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
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private TextField txtFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField txtFieldBalance;
    @FXML
    private Button btnLogIn;
    HelloApplication ha = new HelloApplication();
    @FXML
    private void onSignUpButtonClicked(ActionEvent event){
        String email = txtFieldEmail.getText();
        String username = txtFieldUsername.getText();
        String password = passwordFieldPassword.getText();
        String balanceText = txtFieldBalance.getText();

        if (userExists(username)) {
            showAlert("Error", "Username already exists. Please choose a different username.");
            return;
        }

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || balanceText.isEmpty()) {
            showAlert("Error", "Please fill in all the required fields.");
            return;
        }

        double initialBalance;
        try {
            initialBalance = Double.parseDouble(balanceText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid initial balance. Please enter a valid number.");
            return;
        }
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO users (name, password, email, initial_balance) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, initialBalance);
                preparedStatement.executeUpdate();

                showAlert("Sign Up Successful", "User registered successfully!");
                ha.changeScene("logIn_page.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void changeToLogInPage(ActionEvent event) throws IOException {
        ha.changeScene("logIn_page.fxml");
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean userExists(String username) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT COUNT(*) FROM users WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
