package com.qualitychemicals.qciss.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service

public class FirebaseInit {
    @PostConstruct
    public void initialize() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("./serviceAcount.json");// /home/qci_user/apps/v2/QCiStaffSacco
        //  ./serviceAcount.json

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://qc-sacco-default-rtdb.firebaseio.com")
                .setStorageBucket("qc-sacco.appspot.com")
                .build();

            FirebaseApp.initializeApp(options);

    }
}
