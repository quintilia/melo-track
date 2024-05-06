package com.example.melotrack;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.example.melotrack.MeloTrack.fauth;

public class SignInController implements Initializable {

    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField emailTextfield;
    @FXML
    private Button signinButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFieldBinding accessDataViewModel = new TextFieldBinding();
        emailTextfield.textProperty().bindBidirectional(accessDataViewModel.usernameProperty());
        passwordTextField.textProperty().bindBidirectional(accessDataViewModel.passwordProperty());
        signinButton.disableProperty().bind(accessDataViewModel.isFunctionPossibleProperty().not());
}

    public void handleRegisterButton() throws IOException {
        MeloTrack.setRoot("register-view");
    }

    public void handleSignInButton() throws FirebaseAuthException, IOException {
        UserRecord userRecord = fauth.getUserByEmail(emailTextfield.getText());

        if(emailTextfield.getText().matches(userRecord.getEmail()) && passwordTextField.getText().matches(userRecord.getDisplayName())){
            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION);
            alert.setHeaderText("Signin Successful");
            alert.setTitle("User Signin");
            alert.setContentText("Welcome to MeloTrack.");
            alert.setOnCloseRequest(e -> {
                try {
                    MeloTrack.setRoot("Mp3Player");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            alert.show();
        }else{
            Alert alert = new Alert(
                    Alert.AlertType.WARNING);
            alert.setHeaderText("Please re-enter login credentials!");
            alert.setTitle("Login Error");
            alert.setContentText("Email or password does not match!");
            alert.show();
        }
    }
}