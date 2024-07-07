package org.example.onlineexchange;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ExchangeController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn, exchangeBtn;
    @FXML
    private ChoiceBox<String> srcChoiceBox, desChoiceBox;
    @FXML
    private ImageView exchangeImageView, coin1, coin2, coin3;

    private String srcExchange, desExchange;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Animation for the image view
        TranslateTransition translateTransitionExchange = new TranslateTransition();

        translateTransitionExchange.setDuration(Duration.millis(900));

        translateTransitionExchange.setNode(exchangeImageView);

        translateTransitionExchange.setByY(0);

        translateTransitionExchange.setToY(132);

        translateTransitionExchange.setCycleCount(1);

        translateTransitionExchange.setAutoReverse(false);

        translateTransitionExchange.play();

        // Animation for coin1
        TranslateTransition translateTransitionCoin1 = new TranslateTransition();

        translateTransitionCoin1.setDuration(Duration.millis(1200));

        translateTransitionCoin1.setNode(coin1);

        translateTransitionCoin1.setByX(0);

        translateTransitionCoin1.setToX(50);

        translateTransitionCoin1.setCycleCount(Timeline.INDEFINITE);

        translateTransitionCoin1.setAutoReverse(true);

        translateTransitionCoin1.play();

        // Animation for coin2
        TranslateTransition translateTransitionCoin2 = new TranslateTransition();

        translateTransitionCoin2.setDuration(Duration.millis(1200));

        translateTransitionCoin2.setNode(coin2);

        translateTransitionCoin2.setByX(0);

        translateTransitionCoin2.setToX(50);

        translateTransitionCoin2.setCycleCount(Timeline.INDEFINITE);

        translateTransitionCoin2.setAutoReverse(true);

        translateTransitionCoin2.play();

        // Animation for coin3
        TranslateTransition translateTransitionCoin3 = new TranslateTransition();

        translateTransitionCoin3.setDuration(Duration.millis(1200));

        translateTransitionCoin3.setNode(coin3);

        translateTransitionCoin3.setByX(0);

        translateTransitionCoin3.setToX(-50);

        translateTransitionCoin3.setCycleCount(Timeline.INDEFINITE);

        translateTransitionCoin3.setAutoReverse(true);

        translateTransitionCoin3.play();

        String[] options = {"Dollar", "Toman", "Eur", "Yen", "GBP"};
        ObservableList<String> items = FXCollections.observableArrayList(options);

        srcChoiceBox.setItems(items);

        srcChoiceBox.setValue("Dollar");

        srcChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            srcExchange = items.get(newValue.intValue());

        });

        desChoiceBox.setItems(items);

        desChoiceBox.setValue("Dollar");

        desChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            desExchange = items.get(newValue.intValue());

        });

    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profile/profile.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Profile");

        stage.setScene(scene);

        stage.show();

    }

    public void exchange(ActionEvent event) {



    }
}
