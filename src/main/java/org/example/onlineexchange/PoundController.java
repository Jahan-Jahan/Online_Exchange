package org.example.onlineexchange;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PoundController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn, exchangeBtn;
    @FXML
    private ImageView poundImageView1, poundImageView2, poundImageView3;
    @FXML
    private Label price, change, min, max;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        price.setText(String.valueOf(MainPageController.poundPrice));
        change.setText(MainPageController.changes.get(4));
        min.setText(MainPageController.totalMin.get(4));
        max.setText(MainPageController.totalMax.get(4));

        double ch = Double.parseDouble(change.getText().substring(1));

        if (ch < 0) change.setStyle("-fx-text-fill: red;");
        else if (ch > 0) change.setStyle("-fx-text-fill: green;");

        // Animation for coin1
        TranslateTransition translateTransitionCoin1 = new TranslateTransition();

        translateTransitionCoin1.setDuration(Duration.millis(1000));

        translateTransitionCoin1.setNode(poundImageView1);

        translateTransitionCoin1.setByY(0);

        translateTransitionCoin1.setToY(100);

        translateTransitionCoin1.setCycleCount(Timeline.INDEFINITE);

        translateTransitionCoin1.setAutoReverse(true);

        translateTransitionCoin1.play();

        // Animation for coin2
        TranslateTransition translateTransitionCoin2 = new TranslateTransition();

        translateTransitionCoin2.setDuration(Duration.millis(1200));

        translateTransitionCoin2.setNode(poundImageView2);

        translateTransitionCoin2.setByX(0);

        translateTransitionCoin2.setToX(75);

        translateTransitionCoin2.setCycleCount(Timeline.INDEFINITE);

        translateTransitionCoin2.setAutoReverse(true);

        translateTransitionCoin2.play();

        // Animation for coin3
        TranslateTransition translateTransitionCoin3 = new TranslateTransition();

        translateTransitionCoin3.setDuration(Duration.millis(1200));

        translateTransitionCoin3.setNode(poundImageView3);

        translateTransitionCoin3.setByY(0);

        translateTransitionCoin3.setToY(200);

        translateTransitionCoin3.setCycleCount(Timeline.INDEFINITE);

        translateTransitionCoin3.setAutoReverse(true);

        translateTransitionCoin3.play();

    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }

    public void goToExchange(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("exchange/exchange.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Exchange");

        stage.setScene(scene);

        stage.show();

    }
}
