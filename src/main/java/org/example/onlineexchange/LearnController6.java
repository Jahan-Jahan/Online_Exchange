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

public class LearnController6 implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn;
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        label.setText("Do Your Research: Before investing in any cryptocurrency, understand its fundamentals, the technology behind it, and its use cases.\n" +
                "Diversify Your Portfolio: Don't put all your eggs in one basket. Spread your investments across different cryptocurrencies to mitigate risks.\n" +
                "Stay Informed: The cryptocurrency market is highly volatile. Keep up with the latest news and developments to make informed decisions.\n" +
                "Use Secure Wallets: Choose reliable and secure wallets to store your cryptocurrencies. Consider hardware wallets for added security.\n" +
                "Beware of Scams: Be cautious of schemes promising guaranteed returns or unrealistic profits. Always use reputable exchanges and services.");

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
