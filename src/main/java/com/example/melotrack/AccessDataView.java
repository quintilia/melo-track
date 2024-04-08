package com.example.melotrack;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccessDataView{

    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final ReadOnlyBooleanWrapper signinPossible = new ReadOnlyBooleanWrapper();

    public AccessDataView() {
        signinPossible.bind(username.isNotEmpty());
        signinPossible.bind(password.isNotEmpty());
    }

    public StringProperty usernameProperty() {
        return username;
    }
    public StringProperty passwordProperty() {
        return password;
    }

    public ReadOnlyBooleanProperty isSigninPossibleProperty() {
        return signinPossible.getReadOnlyProperty();
    }
}