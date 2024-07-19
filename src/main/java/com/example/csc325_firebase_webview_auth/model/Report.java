package com.example.csc325_firebase_webview_auth.model;

import com.example.csc325_firebase_webview_auth.view.App;
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
public class Report {

    private String reportID;
    private String userID;
    private String title;
    private String content;
    private Date date;

    public static void addReport(Report report) throws Exception {
        if (report.getReportID() == null || report.getReportID().isEmpty()) {
            report.setReportID(UUID.randomUUID().toString());
        }
        Firestore db = new FirestoreContext().firebase();
        report.setUserID(App.loggedInUserId); // Ensure the user ID is set
        db.collection("reports").document(report.getReportID()).set(report).get();
        System.out.println("Report added: " + report);
    }

    public static void editReport(Report report) throws Exception {
        if (report.getReportID() == null || report.getReportID().isEmpty()) {
            throw new IllegalArgumentException("Report ID must not be null or empty");
        }
        Firestore db = new FirestoreContext().firebase();
        db.collection("reports").document(report.getReportID()).set(report).get();
    }

    public static void deleteReport(String reportID) throws Exception {
        if (reportID == null || reportID.isEmpty()) {
            throw new IllegalArgumentException("Report ID must not be null or empty");
        }
        Firestore db = new FirestoreContext().firebase();
        db.collection("reports").document(reportID).delete().get();
    }

    public static List<Report> viewReports(String userID) throws Exception {
        if (userID == null || userID.isEmpty()) {
            throw new IllegalArgumentException("User ID must not be null or empty");
        }
        Firestore db = new FirestoreContext().firebase();
        List<Report> reports = new ArrayList<>();
        CollectionReference reportsRef = db.collection("reports");
        Query query = reportsRef.whereEqualTo("userID", userID);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            reports.add(document.toObject(Report.class));
        }
        System.out.println("Reports retrieved: " + reports);
        return reports;
    }
}

