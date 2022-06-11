package com.mycompany.maw3id_app;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.IOException;


public class FirebaseService {
     FirebaseDatabase db;
     FirebaseApp firebaseApp;
     

    public FirebaseService() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("C:\\Users\\Hnn Mft\\IdeaProjects\\Maw3id\\src\\main\\resources\\key.json");


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://maw3id-25cc5-default-rtdb.firebaseio.com")
                .build();

       firebaseApp= FirebaseApp.initializeApp(options);

        
    }

    public FirebaseApp getDb() {
        return firebaseApp;
    }
    
}
