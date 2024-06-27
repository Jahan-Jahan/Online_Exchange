package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private ImageView BTCIcon, DOGEIcon, DASHIcon, LTCIcon, soundImageView;
    @FXML
    private Button backBtn, premiumBtn;
    @FXML
    private Label learnLabel, aboutLabel, commentLabel, donateLabel;

    private boolean sound = false;
    private MusicController musicController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        musicController = new MusicController();

        initializeEffects();

    }
    private void initializeEffects() {

        BTCIcon.setOnMouseEntered(event -> {
            BTCIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 7, 0.5, 0, 0);");
        });
        BTCIcon.setOnMouseExited(event -> {
            BTCIcon.setStyle("-fx-effect: none;");
        });

        DOGEIcon.setOnMouseEntered(event -> {
            DOGEIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 7, 0.5, 0, 0);");
        });
        DOGEIcon.setOnMouseExited(event -> {
            DOGEIcon.setStyle("-fx-effect: none;");
        });

        DASHIcon.setOnMouseEntered(event -> {
            DASHIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 7, 0.5, 0, 0);");
        });
        DASHIcon.setOnMouseExited(event -> {
            DASHIcon.setStyle("-fx-effect: none;");
        });

        LTCIcon.setOnMouseEntered(event -> {
            LTCIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 7, 0.5, 0, 0);");
        });
        LTCIcon.setOnMouseExited(event -> {
            LTCIcon.setStyle("-fx-effect: none;");
        });

        learnLabel.setOnMouseEntered(e -> {
            learnLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 15, 0.5, 0, 0);" +
                    "-fx-text-fill: white");
        });
        learnLabel.setOnMouseExited(e -> {
            learnLabel.setStyle("-fx-effect: none;" +
                    "-fx-text-fill: #848484");
        });

        aboutLabel.setOnMouseEntered(e -> {
            aboutLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 15, 0.5, 0, 0);" +
                    "-fx-text-fill: white");
        });
        aboutLabel.setOnMouseExited(e -> {
            aboutLabel.setStyle("-fx-effect: none;" +
                    "-fx-text-fill: #848484");
        });

        commentLabel.setOnMouseEntered(e -> {
            commentLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 15, 0.5, 0, 0);" +
                    "-fx-text-fill: white");
        });
        commentLabel.setOnMouseExited(e -> {
            commentLabel.setStyle("-fx-effect: none;" +
                    "-fx-text-fill: #848484");
        });

        donateLabel.setOnMouseEntered(e -> {
            donateLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 15, 0.5, 0, 0);" +
                    "-fx-text-fill: white");
        });
        donateLabel.setOnMouseExited(e -> {
            donateLabel.setStyle("-fx-effect: none;" +
                    "-fx-text-fill: #848484");
        });

        soundImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.75), 5, 0.5, 0, 0);");
        soundImageView.setOnMouseEntered(e -> {
            soundImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.75), 15, 0.5, 0, 0);");
        });
        soundImageView.setOnMouseExited(e -> {
            soundImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.75), 5, 0.5, 0, 0);");
        });
    }
    public void clickOnProfile() throws IOException {

        // Go to the profile page

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profile/profile.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("profile");

        stage.setScene(scene);

        stage.show();

    }
    public void clickOnSoundIcon() {

        if (sound) {
            sound = false;

            InputStream iconStream = getClass().getResourceAsStream("soundless.png");

            if (iconStream != null) {

                Image icon = new Image(iconStream);

                soundImageView.setImage(icon);
            }

            musicController.stopMusic();

        } else {
            sound = true;

            InputStream iconStream = getClass().getResourceAsStream("sound.png");

            if (iconStream != null) {

                Image icon = new Image(iconStream);

                soundImageView.setImage(icon);
            }

            musicController.playMusic();

        }

    }
    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Login");

        stage.setScene(scene);

        stage.show();

    }
    public void getPremiumAccount(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Soon!");

        alert.setHeaderText("You want Premium account!");

        alert.setContentText("Coming soon! For a month you can get it for 30% off!");

        alert.show();

    }
    public void clickForLearn() throws IOException {

        // Go to the Learn page

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learn/learn.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Learn");

        stage.setScene(scene);

        stage.show();

    }
    public void clickForAbout() throws IOException {

        // Go to the About us page

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("about/about.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("About");

        stage.setScene(scene);

        stage.show();

    }
    public void clickForComment() throws IOException {

        // Go to the Comment page

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("comment/comment.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Comment");

        stage.setScene(scene);

        stage.show();

    }
    public void clickForDonate() {

        // Go to the Pay page

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Donate");

        alert.setHeaderText("Abolfazl Jahangir, the card number is:");

        alert.setContentText("6037-6975-1961-2466\nThank you!");

        alert.show();

    }
}
