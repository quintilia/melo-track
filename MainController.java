import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainController {

    @FXML
    private void handlePlay(ActionEvent event) {
        // Code to handle play button action
        System.out.println("Play button clicked");
    }

    @FXML
    private void handlePause(ActionEvent event) {
        // Code to handle pause button action
        System.out.println("Pause button clicked");
    }

    @FXML
    private void handleStop(ActionEvent event) {
        // Code to handle stop button action
        System.out.println("Stop button clicked");
    }

    @FXML
    private void handleDownload(ActionEvent event) {
        // Code to handle download button action
        System.out.println("Download button clicked");
    }

    @FXML
    private void handleAddToPlaylist(ActionEvent event) {
        // Code to handle add to playlist button action
        System.out.println("Add to playlist button clicked");
    }

    @FXML
    private void handleAddToQueue(ActionEvent event) {
        // Code to handle add to queue button action
        System.out.println("Add to queue button clicked");
    }
}
