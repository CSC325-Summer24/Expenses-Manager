package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ExpenseViewController {

    @FXML
    private TableView<Expense> expensesTable;

    @FXML
    private TableColumn<Expense, String> descriptionColumn;

    @FXML
    private TableColumn<Expense, String> dateColumn;

    @FXML
    private TableColumn<Expense, Double> amountColumn;

    @FXML
    private Button closeButton;

    private ObservableList<Expense> expensesData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        expensesTable.setItems(expensesData);
        loadExpenses();
    }

    private void loadExpenses() {
        try {
            String userID = App.loggedInUserId; // Assumes there's a static field loggedInUserId in the App class
            List<Expense> expenses = Expense.viewExpenses(userID);
            expensesData.setAll(expenses);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void handleEditButtonAction() {
        Expense selectedExpense = expensesTable.getSelectionModel().getSelectedItem();
        if (selectedExpense != null) {
            openExpenseForm(selectedExpense);
            loadExpenses(); // Refresh the table after editing
        } else {
            showAlert("Error", "Please select an expense to edit.");
        }
    }

    @FXML
    private void handleDeleteButtonAction() {
        Expense selectedExpense = expensesTable.getSelectionModel().getSelectedItem();
        if (selectedExpense != null) {
            try {
                Expense.deleteExpense(selectedExpense.getExpenseID());
                showAlert("Success", "Expense deleted successfully.");
                loadExpenses(); // Refresh the table after deleting
            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        } else {
            showAlert("Error", "Please select an expense to delete.");
        }
    }

    @FXML
    private void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void openExpenseForm(Expense expense) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/expenseForm.fxml"));
            Parent root = loader.load();

            ExpenseFormController controller = loader.getController();
            controller.setExpense(expense);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(expense == null ? "Add Expense" : "Edit Expense");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadExpenses(); // Refresh the table after closing the form
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
