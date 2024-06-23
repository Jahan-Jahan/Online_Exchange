package org.example.onlineexchange;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LearnController5 implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn;
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Animation for label
        FadeTransition fadeTransition = new FadeTransition();

        fadeTransition.setDuration(Duration.millis(1000));

        fadeTransition.setNode(label);

        fadeTransition.setFromValue(0.0);

        fadeTransition.setToValue(1.0);

        fadeTransition.play();

    }

    public void backToLearn(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learn/learn.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Learn");

        stage.setScene(scene);

        stage.show();

    }
}
