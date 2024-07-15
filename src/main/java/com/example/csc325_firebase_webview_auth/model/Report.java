package com.example.csc325_firebase_webview_auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Report {

    private  String reportId;

    private String userID;

    private String type;

    private Date generatedDate;


    public static void generateExpenseReport(String userID)
    {

    }

    public static Report viewReport(String userID)
    {
        return null;
    }

    public static void deleteReport(Report report)
    {

    }
}