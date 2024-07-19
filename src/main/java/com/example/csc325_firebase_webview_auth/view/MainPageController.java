package com.example.csc325_firebase_webview_auth.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainPageController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button expressManagerButton;

    @FXML
    private Button aboutUsButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label trackSmartLabel;

    @FXML
    private Label footerLabel;

    @FXML
    private Button learnMoreButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button signUpButton;
    //========================================================================================================
    @FXML
    private FontAwesomeIconView facebookIcon;

    @FXML
    private FontAwesomeIconView linkedinIcon;

    @FXML
    private FontAwesomeIconView twitterIcon;

    @FXML
    private FontAwesomeIconView instagramIcon;
//========================================================================================================

    //init method
    @FXML
    public void initialize(){
        //init code here
    }

    //event handler for ExpressManager button
    @FXML
    private void handleExpenseManagerButtonAction(ActionEvent event){
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


    //learn More button
    @FXML
    private void handleLearnMoreButtonAction(ActionEvent event){
        loadPage("/files/about.fxml");
    }

    //view button
    @FXML
    private void handleViewButtonAction(ActionEvent event){
        loadPage("/files/dashboard.fxml");
    }

    //sign Up button
    @FXML
    private void handleSignUpButtonAction(ActionEvent event){
        loadPage("/files/login.fxml");
    }

    @FXML
    private void handleAddExpenseButtonAction(ActionEvent event) {
        loadPage("/files/addExpense.fxml");
    }

    @FXML
    private void handleViewExpensesButtonAction(ActionEvent event) {
        loadPage("/files/viewExpenses.fxml");
    }

    @FXML
    private void handleAddUserButtonAction(ActionEvent event) {
        loadPage("/files/addUser.fxml");
    }

    @FXML
    private void handleViewUsersButtonAction(ActionEvent event) {
        loadPage("/files/viewUsers.fxml");
    }

    @FXML
    private void handleAddNotificationButtonAction(ActionEvent event) {
        loadPage("/files/addNotification.fxml");
    }

    @FXML
    private void handleViewNotificationsButtonAction(ActionEvent event) {
        loadPage("/files/viewNotifications.fxml");
    }

    @FXML
    private void handleGenerateReportButtonAction(ActionEvent event) {
        loadPage("/files/generateReport.fxml");
    }

    @FXML
    private void handleViewReportsButtonAction(ActionEvent event) {
        loadPage("/files/viewReports.fxml");
    }


    //========================================================================================================
    //handler for Facebook icon
    @FXML
    private void handleFacebookIconClick(MouseEvent event){
        System.out.println("Facebook icon clicked");

    }

    //handler for LinkedIn icon
    @FXML
    private void handleLinkedInIconClick(MouseEvent event){
        System.out.println("LinkedIn icon clicked");

    }

    //handler for Twitter icon
    @FXML
    private void handleTwitterIconClick(MouseEvent event){
        System.out.println("Twitter icon clicked");

    }

    //handler for Instagram icon
    @FXML
    private void handleInstagramIconClick(MouseEvent event){
        System.out.println("Instagram icon clicked");

    }


//========================================================================================================

    //method to load new page
    private void loadPage(String fxml){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) scrollPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


