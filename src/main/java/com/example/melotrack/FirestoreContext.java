package com.example.melotrack;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirestoreContext {

    public Firestore firebase() {
        try {

            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/key.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("gs://melotrack325.appspot.com")
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return FirestoreClient.getFirestore();
    }
}
