package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.utils.NotificationChecker;
import com.example.csc325_firebase_webview_auth.model.FirestoreContext;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Firestore fstore;
    public static FirebaseAuth fauth;
    public static Scene scene;
    private final FirestoreContext contxtFirebase = new FirestoreContext();
    public static String loggedInUserId;
    private NotificationChecker notificationChecker = new NotificationChecker();

    @Override
    public void start(Stage primaryStage) throws Exception {
        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();

        scene = new Scene(loadFXML("/files/mainPage.fxml"), 652, 660); //loads the main page
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/files/main.css")).toExternalForm()); //uses the css
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start checking for notifications
        notificationChecker.startChecking();
    }

    @Override
    public void stop() {
        // Stop the notification checker
        notificationChecker.stopChecking();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
/*
* To anyone reading this or making this class project
* I recommend that you watch the Module 10 video lecture as it will help you a lot
* You can also go over prof. code before you code
* Go over the FirestoneContext - AccessFBView.java - AccessFBView.fxml - WebContainerController - etc.
* and just read how everything is being tied in, specially how to readFirebase or even as simple as switching from a
* different scene
*
* Prof. already gave you the skeleton for the program that you will be creating so there is no need to reinvent the wheel
* Use what is already there to your advantage
*
*/
