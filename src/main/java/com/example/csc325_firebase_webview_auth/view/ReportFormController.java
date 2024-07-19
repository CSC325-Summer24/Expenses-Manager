package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.Report;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;

public class ReportFormController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea contentField;

    @FXML
    private DatePicker dateField;

    @FXML
    private Button saveButton;

    private Report report;

    public void setReport(Report report) {
        this.report = report;
        if (report != null) {
            titleField.setText(report.getTitle());
            contentField.setText(report.getContent());
            dateField.setValue(report.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
        }
    }

    @FXML
    private void handleSaveButtonAction() {
        if (report == null) {
            report = new Report();
        }
        report.setTitle(titleField.getText());
        report.setContent(contentField.getText());
        report.setDate(Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        try {
            if (report.getReportID() == null || report.getReportID().isEmpty()) {
                Report.addReport(report);
            } else {
                Report.editReport(report);
            }
            closeWindow();
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
