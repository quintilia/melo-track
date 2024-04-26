package com.example.mp3pplayer;


import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXSlider;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class Mp3PlayerController implements Initializable {


    private MediaPlayer mediaPlayer;

    @FXML
    private Label lblSongTitle;

    @FXML
    private Label lblSongLength;

    @FXML
    private Label lblArtistName;

    @FXML
    private ImageView pauseNPlay;

    @FXML
    private JFXSlider jfxSlider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        jfxSlider.valueChangingProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null && !newValue) {
                mediaPlayer.seek(Duration.seconds(jfxSlider.getValue()));
            }
        });


    }


    @FXML
    public void chooseMusic(MouseEvent event) {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select your Music");
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            String selectedFile = file.toURI().toString();
            Media media = new Media(selectedFile);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {

                Duration duration = mediaPlayer.getTotalDuration();
                int totalMinutes = (int) duration.toMinutes();
                int totalSeconds = (int) (duration.toSeconds() % 60);
                lblSongLength.setText("0:00 / " + totalMinutes + ":" + String.format("%02d", totalSeconds));

                String title = "Unknown Title";
                String artistName = "Unknown Artist";
                Map<String, Object> metadata = media.getMetadata();
                Set<String> keys = metadata.keySet();
                for (String key : keys) {
                    System.out.println(key + ":" + metadata.get(key));
                }

                if (metadata.containsKey("title")) {
                    title = (String) metadata.get("title");
                }

                if (metadata.containsKey("album artist")) {
                    artistName = (String) metadata.get("album artist");
                }

                lblArtistName.setText(artistName);
                lblSongTitle.setText(title);


                jfxSlider.setMax(duration.toSeconds());
                jfxSlider.setValue(0.0);
                mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                    if (!jfxSlider.isValueChanging()) {
                        int currentMinutes = (int) newValue.toMinutes();
                        int currentSeconds = (int) (newValue.toSeconds() % 60);
                        lblSongLength.setText(currentMinutes + ":" + String.format("%02d", currentSeconds) +
                                " / " + totalMinutes + ":" + String.format("%02d", totalSeconds));

                        jfxSlider.setValue(newValue.toSeconds());
                    }
                });

                mediaPlayer.play();
            });


        }

    }

    @FXML
    void pauseClicked(MouseEvent event) {

        if (mediaPlayer != null)
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                pauseNPlay.setImage(new Image(getClass().getResource("/com/example/images/icons8_play_button_circled_30px_2.png").toExternalForm()));
                mediaPlayer.pause();
            } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                pauseNPlay.setImage(new Image(getClass().getResource("/com/example/images/icons8_pause_24px_1.png").toExternalForm()));
                mediaPlayer.play();
            }


    }

    @FXML
    void forwardClicked(MouseEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(5))); // Forward by 5 seconds
        }
    }

    @FXML
    void rewindClicked(MouseEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.seconds(5))); // Rewind by 5 seconds
        }
    }

}
