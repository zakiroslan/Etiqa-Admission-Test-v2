package com.springboot.firebase.etiqa.springbootreststudent.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializer {
    @PostConstruct
    private void initDB() throws IOException {
        InputStream serviceAccount = this.getClass().getClassLoader()
                .getResourceAsStream("./etiqa-springboot-firebase-firebase-adminsdk-8qlwj-c2f44d2565.json");

        FirebaseOptions builder = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://etiqa-springboot-firebase.firebaseio.com")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(builder);
        }
    }

    public Firestore getFirebase() {
        return FirestoreClient.getFirestore();
    }
}
