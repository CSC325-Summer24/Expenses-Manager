package com.example.csc325_firebase_webview_auth.utils;

import com.example.csc325_firebase_webview_auth.view.App;
import com.example.csc325_firebase_webview_auth.model.Notification;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationChecker {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startChecking() {
        scheduler.scheduleAtFixedRate(this::checkNotifications, 0, 1, TimeUnit.MINUTES);
    }

    private void checkNotifications() {
        try {
            String userID = App.loggedInUserId;
            List<Notification> notifications = Notification.viewNotifications(userID);

            LocalDate today = LocalDate.now();
            for (Notification notification : notifications) {
                LocalDate dueDate = notification.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (dueDate.equals(today)) {
                    Platform.runLater(() -> showAlert("Notification Due", notification.getMessage()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void stopChecking() {
        scheduler.shutdown();
    }
}
