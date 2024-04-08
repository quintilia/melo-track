package com.example.melotrack;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterController {

    @FXML
    private TextField emailTF, phoneNumberTF, nameTF, usernameTF;

    @FXML
    private PasswordField passwordTF;
    @FXML
    private void handleRegisterButton(ActionEvent event) {
        registerUser();
        addData();
    }
    public static void saveUserInfo() {

    }

    public boolean registerUser(){
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(emailTF.getText())
                .setEmailVerified(false)
                .setPassword(passwordTF.getText())
                .setDisplayName(usernameTF.getText())
                .setDisabled(false);
        UserRecord userRecord;
        try {
            userRecord = MeloTrack.fauth.createUser(request);
            System.out.println("New user created with Firebase Uid: " + userRecord.getUid()
                    + " check Firebase > Authentication > Users tab");
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
            return false;
        }
    }
    public void addData() {

        DocumentReference docRef = MeloTrack.fstore.collection("Persons").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameTF.getText());
        data.put("Email", emailTF.getText());
        data.put("PhoneNumber", phoneNumberTF.getText());
        data.put("Username", usernameTF.getText());
        data.put("Password", passwordTF.getText());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("data added");
    }


}