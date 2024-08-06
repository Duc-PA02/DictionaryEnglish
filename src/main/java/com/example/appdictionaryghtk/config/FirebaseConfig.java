package com.example.appdictionaryghtk.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() {
        try {
            // Sử dụng ClassPathResource để tải tệp từ classpath
            ClassPathResource serviceAccountResource = new ClassPathResource(
                    "translate-ghtk-firebase-adminsdk-apy68-d8c6dd470a.json");
            InputStream serviceAccount = serviceAccountResource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("translate-ghtk.appspot.com")
                    .build();

            FirebaseApp app = FirebaseApp.initializeApp(options);
            return app;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}