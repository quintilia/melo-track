package com.example.melotrack;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TextFieldBinding {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final ReadOnlyBooleanWrapper functionPossible = new ReadOnlyBooleanWrapper();

    public TextFieldBinding() {
        functionPossible.bind(name.isNotEmpty());
        functionPossible.bind(email.isNotEmpty());
        functionPossible.bind(phoneNumber.isNotEmpty());
        functionPossible.bind(username.isNotEmpty());
        functionPossible.bind(password.isNotEmpty());
    }
    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }
    public StringProperty usernameProperty() {
        return username;
    }
    public StringProperty passwordProperty() {
        return password;
    }
    public ReadOnlyBooleanProperty isFunctionPossibleProperty() {
        return functionPossible.getReadOnlyProperty();
    }
}