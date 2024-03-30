module com.example.mp3pplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.mp3pplayer to javafx.fxml;
    exports com.example.mp3pplayer;
}