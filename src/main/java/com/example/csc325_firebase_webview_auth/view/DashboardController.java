package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.Expense;
import com.example.csc325_firebase_webview_auth.model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class DashboardController {

    @FXML
    private Button expressManagerButton;
    @FXML
    private Button aboutUsButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button loginButton;

    @FXML
    private Button addExpenseButton;
    @FXML
    private Button editDeleteExpenseButton;
    @FXML
    private Button viewExpensesButton;
    @FXML
    private Button viewReportsButton;
    @FXML
    private Button addReportButton;

    @FXML
    private TableView<Expense> expensesTable;

    @FXML
    private TableColumn<Expense, String> descriptionColumn;
    @FXML
    private TableColumn<Expense, String> dateColumn;
    @FXML
    private TableColumn<Expense, Double> amountColumn;

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
            String userID = App.loggedInUserId;
            List<Expense> expenses = Expense.viewExpenses(userID);
            expensesData.setAll(expenses);
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void handleExpressManagerButtonAction(ActionEvent event) {
        loadPage("/files/mainPage.fxml");
    }

    @FXML
    private void handleAboutUsButtonAction(ActionEvent event) {
        loadPage("/files/about.fxml");
    }

    @FXML
    private void handleDashboardButtonAction(ActionEvent event) {
        loadPage("/files/dashboard.fxml");
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        loadPage("/files/login.fxml");
    }

    @FXML
    private void handleAddExpenseButtonAction(ActionEvent event) {
        openExpenseForm(null);
    }

    @FXML
    private void handleViewExpensesButtonAction(ActionEvent event) {
        openExpenseView();
    }

    @FXML
    private void handleEditDeleteExpenseButtonAction(ActionEvent event) {
        openExpenseView();
    }

    @FXML
    private void handleViewReportsButtonAction(ActionEvent event) {
        openReportView();
    }

    @FXML
    private void handleAddReportButtonAction(ActionEvent event) {
        openReportForm(null);
    }

    @FXML
    private void handleManageNotificationsButtonAction(ActionEvent event) {
        openNotificationManagementView();
    }

    private void openNotificationManagementView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/notificationManagement.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Manage Notifications");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open notification management view. Make sure the FXML file exists at the specified path.");
        }
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
            loadExpenses();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openExpenseView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/expenseView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("View Expenses");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open expense view. Make sure the FXML file exists at the specified path.");
        }
    }

    private void openReportForm(Report report) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/reportForm.fxml"));
            Parent root = loader.load();

            ReportFormController controller = loader.getController();
            controller.setReport(report);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(report == null ? "Add Report" : "Edit Report");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openReportView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/reportView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("View Reports");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open report view. Make sure the FXML file exists at the specified path.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadPage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) expressManagerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
