package com.example.melotrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaylistController {

    @FXML
    private void handleSongsButtonAction(ActionEvent event) {
        try {
            // Load the MP3 Player view
            AnchorPane mp3PlayerView = FXMLLoader.load(getClass().getResource("Mp3Player.fxml"));
            Scene scene = new Scene(mp3PlayerView);

            // Get the current stage (window) from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene on the current stage to switch views
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button addSongButton;

    @FXML
    private TableColumn<?, ?> albumTC;

    @FXML
    private TableColumn<?, ?> artistTC;

    @FXML
    private Button createPlaylistButton;

    @FXML
    private Button deletePlaylistButton;

    @FXML
    private Button deleteSongButton;

    @FXML
    private TableColumn<?, ?> lengthTC;

    @FXML
    private ListView<?> playlistLV;

    @FXML
    private TableView<?> songsTV;

    @FXML
    private TableColumn<?, ?> titleTC;

    @FXML
    void handleAddSongButton(ActionEvent event) {

    }

    @FXML
    void handleCreatePlaylistButton(ActionEvent event) {

    }

    @FXML
    void handleDeletePlaylistButton(ActionEvent event) {

    }

    @FXML
    void handleDeleteSongButton(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

}

