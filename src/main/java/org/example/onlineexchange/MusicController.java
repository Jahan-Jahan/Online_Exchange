package org.example.onlineexchange;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class MusicController {

    @FXML
    private MediaPlayer mediaPlayer;

    public MusicController() {
        String musicFile = "music-to-play/atom.mp3";
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(musicFile)).toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
    }

    public void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
