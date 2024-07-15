package com.example.csc325_firebase_webview_auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String userID;

    private String name;

    private String email;

    private String password;

    public static void register(User user) throws Exception
    {

    }

    public static void login(String userId,String password) throws Exception
    {

    }

    public static void loginByEmail(String email,String password) throws Exception
    {

    }

    public void logout() throws Exception
    {

    }

    public static void updateProfile(User user)
    {

    }

    public void deleteUser(User user) throws Exception
    {

    }

}
