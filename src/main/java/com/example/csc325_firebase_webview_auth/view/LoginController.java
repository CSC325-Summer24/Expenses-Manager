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
    private Button backButton;

    //====================================================================================
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        authenticateUser(username, password);
    }

    //====================================================================================
    @FXML
    private void handleSignupButtonAction(ActionEvent event) {
        String username = signupUsernameField.getText();
        String email = signupEmailField.getText();
        String password = signupPasswordField.getText();
        String retypePassword = signupRetypePasswordField.getText();
        /*
        *to anyone reading this following safety protocols is a must to prevent sorts of attacks
        * like SQL injections, XSS, etc.
        * it would be wiser if you have at least 12 characters long for a password
        * but for a simple class project that is limited
        */
        if (password.length() < 6){
            signupErrorText.setText("Password must be at least 6 characters long.");
            return;
        }

        if (!password.equals(retypePassword)){
            signupErrorText.setText("Passwords do not match.");
            return;
        }

        registerUser(username, email, password);
    }

    //====================================================================================
    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        loadPage("/files/mainPage.fxml");
    }

    //====================================================================================
    private void authenticateUser(String username, String password) {
        try {
            //creates a Firestore query to find a user document with the specified username and password
            Query query = App.fstore.collection("users").whereEqualTo("username", username).whereEqualTo("password", password);

            //executes the query and get an ApiFuture object representing the pending result of the query
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            //this retrieve the documents from the query result
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

            if (!documents.isEmpty()) {
                QueryDocumentSnapshot document = documents.get(0); // Get the first document
                App.loggedInUserId = document.getId(); // Set the logged-in user ID
                System.out.println("Successfully logged in: " + username);
                loadPage("/files/mainPage.fxml");
            } else {
                loginErrorText.setText("Incorrect username/password.");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    //====================================================================================
    private void registerUser(String username, String email, String password) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(username)
                .setDisabled(false);

        try {
            UserRecord userRecord = App.fauth.createUser(request);
            saveUserToFirestore(userRecord.getUid(), username, email, password);
            System.out.println("Successfully created new user: " + username);
            loadPage("/files/mainPage.fxml");
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            signupErrorText.setText("Signup failed. Please try again.");
        }
    }

    //====================================================================================
    //visible in the Authentication inside Firebase
    //make sure to set up the Sign-in method
    private void saveUserToFirestore(String uid, String username, String email, String password) {
        // Asynchronously save the User object to the Firestore database in the "users" collection
        // The document ID is set to the provided uid
        User user = new User(username, email, password);
        ApiFuture<WriteResult> result = App.fstore.collection("users").document(uid).set(user);

    }

    //====================================================================================
    private void loadPage(String fxml) {
        try {
            // Load the FXML file specified by the fxml parameter
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            // Get the current stage from the login button's scene
            Stage stage = (Stage) loginButton.getScene().getWindow();
            // Set the new scene with the loaded FXML root
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //====================================================================================
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
