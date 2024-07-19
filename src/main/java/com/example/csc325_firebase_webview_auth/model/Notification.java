package com.example.csc325_firebase_webview_auth.model;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private String notificationID;
    private String userID;
    private String message;
    private Date timestamp;
    private Double amount;

    public static void addNotification(Notification notification) throws Exception {
        if (notification.getNotificationID() == null || notification.getNotificationID().isEmpty()) {
            notification.setNotificationID(UUID.randomUUID().toString());
        }
        Firestore db = new FirestoreContext().firebase();
        db.collection("notifications").document(notification.getNotificationID()).set(notification).get();
    }

    public static void editNotification(Notification notification) throws Exception {
        Firestore db = new FirestoreContext().firebase();
        db.collection("notifications").document(notification.getNotificationID()).set(notification).get();
    }

    public static void deleteNotification(String notificationID) throws Exception {
        Firestore db = new FirestoreContext().firebase();
        db.collection("notifications").document(notificationID).delete().get();
    }

    public static List<Notification> viewNotifications(String userID) throws Exception {
        Firestore db = new FirestoreContext().firebase();
        List<Notification> notifications = new ArrayList<>();
        CollectionReference notificationsRef = db.collection("notifications");
        Query query = notificationsRef.whereEqualTo("userID", userID);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            notifications.add(document.toObject(Notification.class));
        }
        return notifications;
    }

    public static Notification viewNotification(String notificationID) throws Exception {
        Firestore db = new FirestoreContext().firebase();
        try {
            ApiFuture<DocumentSnapshot> future = db.collection("notifications").document(notificationID).get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return document.toObject(Notification.class);
            } else {
                throw new Exception("Notification Not Found");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
