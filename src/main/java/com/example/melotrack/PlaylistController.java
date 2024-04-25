package com.example.melotrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PlaylistController {

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

