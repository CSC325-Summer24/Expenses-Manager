package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;

public class ExpenseFormController {

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField amountField;

    @FXML
    private DatePicker dueDateField;

    @FXML
    private Button saveButton;

    private Expense expense;

    public void setExpense(Expense expense) {
        this.expense = expense;
        if (expense != null) {
            descriptionField.setText(expense.getDescription());
            amountField.setText(String.valueOf(expense.getAmount()));
            dueDateField.setValue(expense.getDueDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
        }
    }

    @FXML
    private void handleSaveButtonAction() {
        if (expense == null) {
            expense = new Expense();
        }
        expense.setDescription(descriptionField.getText());
        expense.setAmount(Double.parseDouble(amountField.getText()));
        expense.setDueDate(Date.from(dueDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        try {
            if (expense.getExpenseID() == null || expense.getExpenseID().isEmpty()) {
                Expense.addExpense(expense);
            } else {
                Expense.editExpense(expense);
            }
            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
