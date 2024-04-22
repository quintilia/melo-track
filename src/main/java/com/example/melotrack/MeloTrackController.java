package com.example.melotrack;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class MeloTrackController implements Initializable {

    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private Button signinButton, registerButton;

    private boolean key;
    String username;
    String password;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFieldBinding accessDataViewModel = new TextFieldBinding();
        usernameTextfield.textProperty().bindBidirectional(accessDataViewModel.usernameProperty());
        passwordTextField.textProperty().bindBidirectional(accessDataViewModel.passwordProperty());
        signinButton.disableProperty().bind(accessDataViewModel.isFunctionPossibleProperty().not());
    }


    public void handleRegisterButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        Stage registerPage = (Stage) registerButton.getScene().getWindow();
        registerPage.setScene(new Scene(root));
    }

    public void handleSignInButton() throws IOException {
        signIn();
        if(username.matches(usernameTextfield.getText()) && password.matches(passwordTextField.getText())){
            System.out.println("Signed in successfully");

            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage registerPage = (Stage) registerButton.getScene().getWindow();
            registerPage.setScene(new Scene(root));
        }else{
            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION);
            alert.setHeaderText("Please re-enter login credentials!");
            alert.setTitle("Login Error");
            alert.setContentText("Username or password does not match!");
            alert.show();
        }
    }
    public boolean signIn() {
        key = false;
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  MeloTrack.fstore.collection("Persons").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if(!documents.isEmpty())
            {
                for (QueryDocumentSnapshot document : documents)
                {
                    username = String.valueOf(document.getData().get("Username"));
                    password = String.valueOf(document.getData().get("Password"));
                }
            }
            else
            {
                System.out.println("No data");
            }
            key=true;

        }
        catch (InterruptedException | ExecutionException ex)
        {
            ex.printStackTrace();
        }
        return key;
    }
    public void disableSignInButton(){
        }

}