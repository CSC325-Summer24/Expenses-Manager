package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.util.List;

public class UserController {

    @FXML
    private TextField userIdField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private void addUser() {
        try {
            User user = User.builder()
                    .userID(userIdField.getText())
                    .username(usernameField.getText())
                    .email(emailField.getText())
                    .password(passwordField.getText())
                    .build();
            User.addUser(user);
            showAlert("Success", "User added successfully.");
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void editUser() {
        try {
            User user = User.builder()
                    .userID(userIdField.getText())
                    .username(usernameField.getText())
                    .email(emailField.getText())
                    .password(passwordField.getText())
                    .build();
            User.editUser(user);
            showAlert("Success", "User edited successfully.");
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void deleteUser() {
        try {
            User user = User.builder()
                    .userID(userIdField.getText())
                    .build();
            User.deleteUser(user);
            showAlert("Success", "User deleted successfully.");
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void viewUsers() {
        try {
            List<User> users = User.viewUsers();
            // Display users in your preferred way, e.g., a TableView
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
