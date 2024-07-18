package com.example.csc325_firebase_webview_auth.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class AboutController {

    @FXML
    private Button dashboardButton;

    @FXML
    private Button expressManagerButton;

    @FXML
    private Button loginButton;


    //event handler for ExpressManager button
    @FXML
    private void handleExpressManagerButtonAction(ActionEvent event){
        loadPage("/files/mainPage.fxml");
    }

    //handler for AboutUs button
    @FXML
    private void handleAboutUsButtonAction(ActionEvent event){
        loadPage("/files/about.fxml");
    }

    //handler for Dashboard button
    @FXML
    private void handleDashboardButtonAction(ActionEvent event){
        loadPage("/files/dashboard.fxml");
    }

    //handler for LogIn button
    @FXML
    private void handleLoginButtonAction(ActionEvent event){
        loadPage("/files/login.fxml");
    }

    // Method to load a new page
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
