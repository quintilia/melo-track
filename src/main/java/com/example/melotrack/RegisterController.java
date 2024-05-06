package com.example.melotrack;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import static com.example.melotrack.MeloTrack.fauth;

public class RegisterController implements Initializable {

    @FXML
    private Button registerButton;

    @FXML
    private TextField emailTF, phoneNumberTF, nameTF;

    @FXML
    private PasswordField passwordTF;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFieldBinding textFieldBindingModel = new TextFieldBinding();
        nameTF.textProperty().bindBidirectional(textFieldBindingModel.nameProperty());
        emailTF.textProperty().bindBidirectional(textFieldBindingModel.emailProperty());
        phoneNumberTF.textProperty().bindBidirectional(textFieldBindingModel.phoneNumberProperty());
        passwordTF.textProperty().bindBidirectional(textFieldBindingModel.passwordProperty());
        registerButton.disableProperty().bind(textFieldBindingModel.isFunctionPossibleProperty().not());
    }
    @FXML
    private void handleRegisterButton() {
        registerUser(new User(nameTF.getText(),emailTF.getText(), phoneNumberTF.getText(), passwordTF.getText()));
    }
    @FXML
    public void handleReturnToSignin() throws IOException {
        MeloTrack.setRoot("signin-view");
    }

    public void registerUser(User p) {
        //save user info into "Users" collection with email as document ID
        ApiFuture<WriteResult> future = MeloTrack.db.collection("Users").document(p.getEmail()).set(p);
        if(nameTF.getText().matches("[A-Z][A-Za-z\\s]*") && emailTF.getText().matches("[\\w]+@email.com") &&
                phoneNumberTF.getText().matches("[\\d]{10}") && passwordTF.getText().matches("[\\S]{7,}")) {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setUid(p.getEmail())
                    .setEmail(p.getEmail())
                    .setEmailVerified(false)
                    .setPassword(p.getPassword())
                    .setDisplayName(p.getPassword())
                    .setDisabled(false);
            UserRecord userRecord;
            try {
                userRecord = fauth.createUser(request);
                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION);
                alert.setHeaderText("New User Registered");
                alert.setTitle("User Registration");
                alert.setContentText(nameTF.getText() + ", Thank you for registering for MeloTrack.");
                alert.setOnCloseRequest(e -> {
                    try {
                        handleReturnToSignin();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                alert.show();
            } catch (FirebaseAuthException ignored) {

            }
        }else {
                Alert alert = new Alert(
                        Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Input");
                alert.setTitle("User Registration");
                alert.setContentText("Please re-enter registration info.");
                alert.show();
        }
    }
}