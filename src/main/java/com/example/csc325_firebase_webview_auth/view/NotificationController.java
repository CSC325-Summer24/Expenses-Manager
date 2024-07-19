package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class NotificationController {

    @FXML
    private TextField notificationIdField;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField messageField;

    @FXML
    private TextField notificationNameField;

    @FXML
    private DatePicker dueDateField;

    @FXML
    private TextField amountField;

    @FXML
    private TableView<Notification> notificationsTable;

    @FXML
    private TableColumn<Notification, String> notificationNameColumn;

    @FXML
    private TableColumn<Notification, Date> dueDateColumn;

    @FXML
    private TableColumn<Notification, Double> amountColumn;

    @FXML
    private Button addNotificationButton;

    @FXML
    private Button editNotificationButton;

    @FXML
    private Button deleteNotificationButton;

    private ObservableList<Notification> notificationsData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        notificationNameColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        notificationsTable.setItems(notificationsData);
        loadNotifications();
    }

    private void loadNotifications() {
        try {
            String userID = App.loggedInUserId; // Assumes there's a static field loggedInUserId in the App class
            List<Notification> notifications = Notification.viewNotifications(userID);
            notificationsData.setAll(notifications);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addNotification() {
        try {
            Notification notification = Notification.builder()
                    .userID(App.loggedInUserId)
                    .message(notificationNameField.getText())
                    .timestamp(Date.from(dueDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .amount(Double.parseDouble(amountField.getText()))
                    .build();
            Notification.addNotification(notification);
            showAlert("Success", "Notification added successfully.");
            loadNotifications(); // Refresh the table
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void editNotification() {
        Notification selectedNotification = notificationsTable.getSelectionModel().getSelectedItem();
        if (selectedNotification != null) {
            try {
                selectedNotification.setMessage(notificationNameField.getText());
                selectedNotification.setTimestamp(Date.from(dueDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                selectedNotification.setAmount(Double.parseDouble(amountField.getText()));
                Notification.editNotification(selectedNotification);
                showAlert("Success", "Notification edited successfully.");
                loadNotifications(); // Refresh the table
            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        } else {
            showAlert("Error", "Please select a notification to edit.");
        }
    }

    @FXML
    private void deleteNotification() {
        Notification selectedNotification = notificationsTable.getSelectionModel().getSelectedItem();
        if (selectedNotification != null) {
            try {
                Notification.deleteNotification(selectedNotification.getNotificationID());
                showAlert("Success", "Notification deleted successfully.");
                loadNotifications(); // Refresh the table
            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        } else {
            showAlert("Error", "Please select a notification to delete.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleCloseButtonAction() {
        Stage stage = (Stage) addNotificationButton.getScene().getWindow();
        stage.close();
    }
}

