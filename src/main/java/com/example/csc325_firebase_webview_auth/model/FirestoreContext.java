package com.example.csc325_firebase_webview_auth.model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;

/**
 *
 * @author MoaathAlrajab
 */
public class FirestoreContext {

    private static Firestore firestore;

    public Firestore firebase() {
        if (firestore == null) {
            synchronized (FirestoreContext.class) {
                if (firestore == null) {
                    try {
                        FirebaseOptions options = new FirebaseOptions.Builder()
                                .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("/files/key.json")))
                                .build();
                        if (FirebaseApp.getApps().isEmpty()) {
                            FirebaseApp.initializeApp(options);
                            System.out.println("Firebase is initialized");
                        }
                        firestore = FirestoreClient.getFirestore();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return firestore;
    }
}
