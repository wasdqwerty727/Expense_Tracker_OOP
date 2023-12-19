package com.example.expense_tracker_oop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExpenseController {
    @FXML
    private ChoiceBox categoryChoiceBox;
    @FXML
    private ChoiceBox typeChoiceBox;
    @FXML
    private ChoiceBox currencyChoiceBox;
    @FXML
    private TextField txtFieldAmount;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private Label labelWelcomeUser;
    @FXML
    private Label labelUserInitialBalance;
    @FXML
    private Label labelDate;
    @FXML
    private Button btnLogout;
    public void setUsername(String username) {
        labelWelcomeUser.setText("Welcome " + username);
    }
    public void setBalance(double balance) {
        labelUserInitialBalance.setText("Your balance: " + balance);
    }

    @FXML
    private void initialize() {
        updateCurrentDate();
        ObservableList<String> types = FXCollections.observableArrayList("Income", "Expense");
        typeChoiceBox.setItems(types);
        loadCategories();
        loadCurrencies();
    }
    private void loadCategories() {
        try {
            List<Category> categories = CategoryDAO.getAllCategories();
            ObservableList<String> categoryNames = FXCollections.observableArrayList();

            for (Category category : categories) {
                categoryNames.add(category.getName());
            }

            categoryChoiceBox.setItems(categoryNames);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
    }
    private void loadCurrencies() {
        try {
            List<Currency> currencies = CurrencyDAO.getAllCurrencies();
            ObservableList<String> currencyNames = FXCollections.observableArrayList();

            for (Currency currency : currencies) {
                currencyNames.add(currency.getName());
            }

            currencyChoiceBox.setItems(currencyNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedDate = currentDate.format(formatter);

        labelDate.setText(formattedDate);
    }
    @FXML
    private void onLogoutButtonClicked() {
        try {
            HelloApplication.changeScene("logIn_page.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onAddButtonClicked(ActionEvent event) {
        try {
            // Get inputs from UI elements
            String categoryName = (String) categoryChoiceBox.getValue();
            String transactionType = (String) typeChoiceBox.getValue();
            String currencyName = (String) currencyChoiceBox.getValue();

            // Validate inputs
            if (categoryName == null || transactionType == null || currencyName == null ||
                    txtFieldAmount.getText().isEmpty() || dateDatePicker.getValue() == null) {
                showAlert("Error", "Please fill in all the required fields.");
                return;
            }

            double amount = Double.parseDouble(txtFieldAmount.getText());
            LocalDate transactionDate = dateDatePicker.getValue();

            // Create a Transaction object
            Transaction transaction = new Transaction(categoryName, transactionType, currencyName, amount, transactionDate);

            // Add the transaction to the database
            TransactionDAO.addTransaction(transaction);

            // Optionally, you can clear the input fields or update the UI as needed
            clearInputFields();

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount. Please enter a valid number.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error adding the transaction to the database.");
        }
    }

    private void showAlert(String title, String content) {
        // Implement your showAlert method here (similar to what you've done in your previous controllers)
    }

    private void clearInputFields() {
        // Clear the input fields if needed
        categoryChoiceBox.setValue(null);
        typeChoiceBox.setValue(null);
        currencyChoiceBox.setValue(null);
        txtFieldAmount.clear();
        dateDatePicker.setValue(null);
    }

}