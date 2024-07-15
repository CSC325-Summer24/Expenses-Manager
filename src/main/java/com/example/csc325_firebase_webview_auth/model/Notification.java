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

public class Notification {
    private String notificationID;

    private String userId;

    private String message;

    private Date date;

    private Boolean isRead;

    public static void sendNotification(Notification notification) throws  Exception
    {

    }

    public static void markAsRead(Notification notification) throws Exception
    {

    }

    public static void viewNotification(String notificationID) throws Exception
    {

    }


    public static void deleteNotification(Notification notification) throws Exception
    {

    }


}