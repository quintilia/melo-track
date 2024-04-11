package com.example.mp3pplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import java.util.Map;
import java.io.File;
import javafx.util.Duration;

public class HelloController {

    @FXML
    private Label chooseMusic;
    private MediaPlayer mediaPlayer;

    @FXML
    void chooseMusic(MouseEvent event) {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Select your Music");
        File file=chooser.showOpenDialog(null);
        if(file != null){
            String selectedFile=file.toURI().toString();
            Media media=new Media(selectedFile);
            mediaPlayer=new MediaPlayer(media);
            mediaPlayer.setOnReady(() ->{
                chooseMusic.setText(file.getName());
                double durationInSeconds = media.getDuration().toSeconds();
                int minutes = (int) durationInSeconds / 60;
                int seconds = (int) durationInSeconds % 60;
                System.out.println("Duration: " + minutes + " minutes " + seconds + " seconds");
                // Getting metadata for the title
                String title = "Unknown Title";
                Map<String, Object> metadata = media.getMetadata();
                if (metadata.containsKey("title")) {
                    title = (String) metadata.get("title");
                }
                // Printing title and duration
                System.out.println("Title: " + title);
            });


        }

    }

    @FXML
    void pause(MouseEvent event) {
mediaPlayer.pause();
    }

    @FXML
    void play(MouseEvent event) {
mediaPlayer.play();
    }

    @FXML
    void stop(MouseEvent event) {
mediaPlayer.stop();
    }

    public void forward(ActionEvent actionEvent) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(5))); // Forward by 5 seconds (adjust as needed)
        }
    }

    public void rewind(ActionEvent actionEvent) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.seconds(5))); // Rewind by 5 seconds (adjust as needed)
        }
    }


}
