package com.example.csc325_firebase_webview_auth.model;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String userID;
    private String username;
    private String email;
    private String password;

    public static void addUser(User user) throws Exception {
        Firestore db = new FirestoreContext().firebase();
        ApiFuture<WriteResult> future = db.collection("users").document(user.getUserID()).set(user);
        future.get();
    }

    public static void editUser(User user) throws Exception {
        Firestore db = new FirestoreContext().firebase();
        ApiFuture<WriteResult> future = db.collection("users").document(user.getUserID()).set(user);
        future.get();
    }

    public static void deleteUser(User user) throws Exception {
        Firestore db = new FirestoreContext().firebase();
        ApiFuture<WriteResult> future = db.collection("users").document(user.getUserID()).delete();
        future.get();
    }

    public static List<User> viewUsers() throws Exception {
        Firestore db = new FirestoreContext().firebase();
        List<User> users = new ArrayList<>();
        CollectionReference usersRef = db.collection("users");
        try {
            ApiFuture<QuerySnapshot> future = usersRef.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                User user = document.toObject(User.class);
                users.add(user);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Error retrieving users", e);
        }
        return users;
    }

    public static User viewUser(String userID) throws Exception {
        Firestore db = new FirestoreContext().firebase();
        try {
            ApiFuture<DocumentSnapshot> future = db.collection("users").document(userID).get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return document.toObject(User.class);
            } else {
                throw new Exception("User Not Found");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
