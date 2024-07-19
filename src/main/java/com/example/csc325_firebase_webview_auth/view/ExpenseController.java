package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ExpenseController {

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Expense> expensesTable;
    @FXML
    private TableColumn<Expense, String> descriptionColumn;
    @FXML
    private TableColumn<Expense, String> dateColumn;
    @FXML
    private TableColumn<Expense, Double> amountColumn;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField amountField;
    @FXML
    private TextField dateField;

    private final ObservableList<Expense> expensesData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        expensesTable.setItems(expensesData);

        loadExpenses();
    }

    private void loadExpenses() {
        try {
            String userID = App.loggedInUserId;
            List<Expense> expenses = Expense.viewExpenses(userID);
            expensesData.setAll(expenses);
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        try {
            Expense expense = new Expense();
            expense.setDescription(descriptionField.getText());
            expense.setAmount(Double.parseDouble(amountField.getText()));
            expense.setDueDate(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText()));
            expense.setUserID(App.loggedInUserId);
            Expense.addExpense(expense);
            showAlert("Success", "Expense added successfully.");
            loadExpenses();
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void handleEditButtonAction(ActionEvent event) {
        try {
            Expense selectedExpense = expensesTable.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                selectedExpense.setDescription(descriptionField.getText());
                selectedExpense.setAmount(Double.parseDouble(amountField.getText()));
                selectedExpense.setDueDate(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText()));
                Expense.editExpense(selectedExpense);
                showAlert("Success", "Expense updated successfully.");
                loadExpenses();
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        try {
            Expense selectedExpense = expensesTable.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                Expense.deleteExpense(selectedExpense.getExpenseID());
                showAlert("Success", "Expense deleted successfully.");
                loadExpenses();
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
