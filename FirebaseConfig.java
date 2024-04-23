import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConfig {

    public static void initFirebase() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("path/to/firebase-credentials.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://your-firebase-database-url.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
