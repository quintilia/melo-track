import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class PlaylistController {

    @FXML
    private ListView<String> playlistView;

    public void initialize() {
        // Initialize playlist view
        ObservableList<String> playlists = FXCollections.observableArrayList(
                "Playlist 1",
                "Playlist 2",
                "Playlist 3"
        );
        playlistView.setItems(playlists);
    }

    @FXML
    private void handleCreatePlaylist() {
        // Code to handle create playlist button action
        System.out.println("Create Playlist button clicked");
    }

    @FXML
    private void handleDeletePlaylist() {
        // Code to handle delete playlist button action
        System.out.println("Delete Playlist button clicked");
    }
}
