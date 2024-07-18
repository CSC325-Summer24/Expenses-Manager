package com.example.csc325_firebase_webview_auth.view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text loginErrorText;

    @FXML
    private TextField signupUsernameField;

    @FXML
    private TextField signupEmailField;

    @FXML
    private PasswordField signupPasswordField;

    @FXML
    private PasswordField signupRetypePasswordField;

    @FXML
    private Button signupButton;

    @FXML
    private Text signupErrorText;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        authenticateUser(username, password);
    }

    @FXML
    private void handleSignupButtonAction(ActionEvent event) {
        String username = signupUsernameField.getText();
        String email = signupEmailField.getText();
        String password = signupPasswordField.getText();
        String retypePassword = signupRetypePasswordField.getText();

        if (password.length() < 6) {
            signupErrorText.setText("Password must be at least 6 characters long.");
            return;
        }

        if (!password.equals(retypePassword)) {
            signupErrorText.setText("Passwords do not match.");
            return;
        }

        registerUser(username, email, password);
    }

    private void authenticateUser(String username, String password) {
        try {
            Query query = App.fstore.collection("users").whereEqualTo("username", username).whereEqualTo("password", password);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

            if (!documents.isEmpty()) {
                loadPage("/files/mainPage.fxml");
            } else {
                loginErrorText.setText("Incorrect username/password.");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void registerUser(String username, String email, String password) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(username)
                .setDisabled(false);

        try {
            UserRecord userRecord = App.fauth.createUser(request);
            saveUserToFirestore(userRecord.getUid(), username, email, password);
            loadPage("/files/mainPage.fxml");
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            signupErrorText.setText("Signup failed. Please try again.");
        }
    }

    private void saveUserToFirestore(String uid, String username, String email, String password) {
        User user = new User(username, email, password);
        ApiFuture<WriteResult> result = App.fstore.collection("users").document(uid).set(user);
    }

    private void loadPage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Static inner class to represent a User
    public static class User {
        private String username;
        private String email;
        private String password;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
