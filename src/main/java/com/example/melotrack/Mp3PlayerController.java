package com.example.melotrack;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.melotrack.LoadPlaylistController;
import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private List<String> playList;
    private int currentSongIndex;
    Map<String, String> selectedTracks;

    @FXML
    private Pane rightPane;

    private String[] artists = {"Artist1", "Artist2", "Artist3", "Artist4"};
    private String[] songs = {"Song1", "Song2", "Song3", "Song4"};
    private String[] durations = {"04:30", "03:45", "05:10", "04:00"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        jfxSlider.valueChangingProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null && !newValue) {
                mediaPlayer.seek(Duration.seconds(jfxSlider.getValue()));
            }
        });

        selectedTracks = new HashMap<>();
        initializeTracks();

    }

    private void initializeTracks() {
        int layoutX = 40;
        AtomicInteger layoutY = new AtomicInteger(252);
        List<File> mp3Files = new ArrayList<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL directoryUrl = classLoader.getResource("com/example/selectedTracks/");
            if (directoryUrl != null) {
                Path directoryPath = Paths.get(directoryUrl.toURI());
                Files.walk(directoryPath)
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().toLowerCase().endsWith(".mp3"))
                        .map(Path::toFile)
                        .forEach(mp3Files::add);

                for (File file : mp3Files) {


                    String selectedFile = file.toURI().toString();

                    Media media = new Media(selectedFile);
                    MediaPlayer mPlayer = new MediaPlayer(media);
                    mPlayer.setOnReady(() -> {
                        String title = "Unknown Title";

                        String artistName = "Unknown Artist";
                        Map<String, Object> metadata = media.getMetadata();

                        if (metadata.containsKey("title")) {
                            title = (String) metadata.get("title");
                        }

                        Duration duration = mPlayer.getTotalDuration();
                        int totalMinutes = (int) duration.toMinutes();
                        int totalSeconds = (int) (duration.toSeconds() % 60);
                        String strDuration = totalMinutes + ":" + String.format("%02d", totalSeconds);


                        if (metadata.containsKey("album artist")) {
                            artistName = (String) metadata.get("album artist");
                        }


                        Pane trackPane = createTrackPane(artistName, title, strDuration, layoutX, layoutY.get());

                        selectedTracks.put(artistName + title + strDuration, selectedFile);
                        rightPane.getChildren().add(trackPane);
                        layoutY.addAndGet(80);
                    });

                }
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private Pane createTrackPane(String artist, String song, String duration, int layoutX, int layoutY) {
        Pane trackPane = new Pane();
        trackPane.getStyleClass().add("playbox");
        trackPane.setPrefSize(410, 76);
        trackPane.setLayoutX(layoutX);
        trackPane.setLayoutY(layoutY);
        ImageView imageView = new ImageView(new Image(getClass().getResource("/com/example/images/mic2.jpg").toExternalForm()));
        imageView.setLayoutX(7);
        imageView.setLayoutY(7);
        imageView.setFitWidth(69);
        imageView.setFitHeight(61);

        Label artistLabel = new Label(artist);
        artistLabel.setLayoutX(84);
        artistLabel.setLayoutY(20);
        artistLabel.setTextFill(Color.web("#fffdfd"));
        artistLabel.setFont(Font.font("Calibri", 16));

        Label songLabel = new Label(song);
        songLabel.setLayoutX(85);
        songLabel.setLayoutY(38);
        songLabel.setTextFill(Color.web("#fffdfd"));
        songLabel.setFont(Font.font("Calibri", 14));

        Label durationLabel = new Label(duration);
        durationLabel.setLayoutX(277);
        durationLabel.setLayoutY(31);
        durationLabel.setTextFill(Color.web("#fffdfd"));
        durationLabel.setFont(Font.font("Calibri", 12));

        ImageView playButton = new ImageView(new Image(getClass().getResource("/com/example/images/icons8_play_button_circled_30px_3.png").toExternalForm()));
        playButton.setLayoutX(329);
        playButton.setLayoutY(27);
        playButton.setFitWidth(23);
        playButton.setFitHeight(23);
        playButton.setOnMouseClicked(event -> handleTrackPlayButtonClick(artist, song, duration));

        trackPane.getChildren().addAll(imageView, artistLabel, songLabel, durationLabel, playButton);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(javafx.scene.effect.BlurType.ONE_PASS_BOX);
        dropShadow.setColor(Color.web("#323232"));
        trackPane.setEffect(dropShadow);

        return trackPane;
    }

    private void handleTrackPlayButtonClick(String artistName, String title, String duration) {
        String selectedFile = selectedTracks.get(artistName + title + duration);
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
            mediaPlayer.pause();

        setSong(selectedFile);
    }


    @FXML
    public void chooseMusic(MouseEvent event) {

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle("Select your Music");
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            String selectedFile = file.toURI().toString();
            setSong(selectedFile);


        }

    }

    private void setSong(String selectedFile) {
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

        mediaPlayer.setOnEndOfMedia(() -> {
            playNextSong(null);
        });
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

    @FXML
    public void createPlaylist(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreatePlaylist.fxml"));
        Parent root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Create Playlist");
        dialogStage.setScene(new Scene(root));
        dialogStage.setResizable(false);
        dialogStage.showAndWait();

    }

    @FXML
    public void loadPlaylist(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadPlaylist.fxml"));
        Parent root = loader.load();
        LoadPlaylistController controller = loader.getController();
        controller.getSongPathsListView().getItems().clear();
        playList = new ArrayList<>();
        currentSongIndex = 0;
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
            mediaPlayer.pause();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Playlist File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());


        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    controller.songPathsListView.getItems().add(line);
                    playList.add(line);
                }


                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setTitle("Load Playlist");
                dialogStage.setScene(new Scene(root));
                //  dialogStage.setResizable(false);
                dialogStage.showAndWait();

                if (controller.isPlayButtonClicked()) {
                    startPlayList();
                }

            } catch (IOException e) {
                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error occurred while loading playlist.");
                alert.showAndWait();
            }
        }
    }

    void startPlayList() {

        if (playList != null && !playList.isEmpty()) {
            playNextSong(null);
        }
    }

    @FXML
    public void playNextSong(MouseEvent mouseEvent) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
            mediaPlayer.pause();


        if (playList != null && currentSongIndex < playList.size()) {
            setSong(playList.get(currentSongIndex));
            currentSongIndex++;
        } else {

            currentSongIndex = 0;
        }
    }

    @FXML
    public void playPreviousSong(MouseEvent mouseEvent) {

        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
            mediaPlayer.pause();

        if (playList != null && currentSongIndex > 0) {
            currentSongIndex--;
            setSong(playList.get(currentSongIndex));
        } else if (currentSongIndex == 0 && playList != null && !playList.isEmpty()) {
            currentSongIndex = playList.size() - 1;
            setSong(playList.get(currentSongIndex));
        }
    }


}
