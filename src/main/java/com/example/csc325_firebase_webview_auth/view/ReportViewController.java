package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.Report;
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
import java.util.Date;
import java.util.List;

public class ReportViewController {

    @FXML
    private TableView<Report> reportsTable;

    @FXML
    private TableColumn<Report, String> titleColumn;

    @FXML
    private TableColumn<Report, String> contentColumn;

    @FXML
    private TableColumn<Report, Date> dateColumn;

    @FXML
    private Button closeButton;

    private ObservableList<Report> reportsData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        reportsTable.setItems(reportsData);
        loadReports();
    }

    private void loadReports() {
        try {
            String userID = App.loggedInUserId; // Assumes there's a static field loggedInUserId in the App class
            List<Report> reports = Report.viewReports(userID);
            reportsData.setAll(reports);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void handleEditButtonAction() {
        Report selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            openReportForm(selectedReport);
            loadReports(); // Refresh the table after editing
        } else {
            showAlert("Error", "Please select a report to edit.");
        }
    }

    @FXML
    private void handleDeleteButtonAction() {
        Report selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            try {
                Report.deleteReport(selectedReport.getReportID());
                showAlert("Success", "Report deleted successfully.");
                loadReports(); // Refresh the table after deleting
            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        } else {
            showAlert("Error", "Please select a report to delete.");
        }
    }

    @FXML
    private void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
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
            loadReports(); // Refresh the table after closing the form
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
