package com.example.melotrack;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class MeloTrackController implements Initializable {

    public PasswordField passwordTextField;
    public TextField usernameTextfield;
    @FXML
    private Button signinButton, registerButton;

    private boolean key;
    String username;
    String password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AccessDataView accessDataViewModel = new AccessDataView();
        usernameTextfield.textProperty().bindBidirectional(accessDataViewModel.usernameProperty());
        passwordTextField.textProperty().bindBidirectional(accessDataViewModel.passwordProperty());
        signinButton.disableProperty().bind(accessDataViewModel.isSigninPossibleProperty().not());
    }

    public void handleRegisterButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        Stage registerPage = (Stage) registerButton.getScene().getWindow();
        registerPage.setScene(new Scene(root));
    }

    public void handleSignInButton() {
        signIn();
        if(username.matches(usernameTextfield.getText()) && password.matches(passwordTextField.getText())){
            System.out.println("Signed in successfully");
        }else{
            System.out.println("Please enter correct signin credentials");
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
}