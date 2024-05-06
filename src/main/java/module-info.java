module com.example.mp3pplayer {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires com.google.api.apicommon;
    requires com.jfoenix;

    opens com.example.melotrack to javafx.fxml, google.cloud.firestore;
    exports com.example.melotrack;
}