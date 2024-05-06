package com.example.melotrack;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoadPlaylistController {

    @FXML
    public ListView<String> songPathsListView;

    private boolean playButtonClicked;

    public ListView<String> getSongPathsListView() {
        return songPathsListView;
    }

    @FXML
    public void playButtonClicked(MouseEvent mouseEvent) {
        playButtonClicked = true;
        Stage stage = (Stage) songPathsListView.getScene().getWindow();
        stage.close();
    }

    public boolean isPlayButtonClicked() {
        return playButtonClicked;

    }
}
