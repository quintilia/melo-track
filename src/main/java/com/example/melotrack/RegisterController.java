package com.example.melotrack;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

public class RegisterController implements Initializable {

    @FXML
    private Button returnToSigninButton, registerButton;

    @FXML
    private TextField emailTF, phoneNumberTF, nameTF, usernameTF;

    @FXML
    private PasswordField passwordTF;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFieldBinding textFieldBindingModel = new TextFieldBinding();
        nameTF.textProperty().bindBidirectional(textFieldBindingModel.nameProperty());
        emailTF.textProperty().bindBidirectional(textFieldBindingModel.emailProperty());
        phoneNumberTF.textProperty().bindBidirectional(textFieldBindingModel.phoneNumberProperty());
        usernameTF.textProperty().bindBidirectional(textFieldBindingModel.usernameProperty());
        passwordTF.textProperty().bindBidirectional(textFieldBindingModel.passwordProperty());
        registerButton.disableProperty().bind(textFieldBindingModel.isFunctionPossibleProperty().not());
    }
    @FXML
    private void handleRegisterButton() {
        registerUser();
        addData();
    }
    @FXML
    public void handleReturnToSignin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("melotrack-view.fxml"));
        Stage registerPage = (Stage) returnToSigninButton.getScene().getWindow();
        registerPage.setScene(new Scene(root));
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
        System.out.println("data added" + result);
    }
}