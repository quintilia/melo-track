module com.example.melotrack {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.melotrack to javafx.fxml;
    exports com.example.melotrack;
}