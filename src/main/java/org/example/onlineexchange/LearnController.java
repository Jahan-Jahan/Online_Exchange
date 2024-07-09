package org.example.onlineexchange;

import javafx.animation.TranslateTransition;
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

public class LearnController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label label1, label2, label3, label4, label5, label6;
    @FXML
    private Button backBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Animation for label1
        TranslateTransition translateTransition1 = new TranslateTransition();

        translateTransition1.setDuration(Duration.millis(800));

        translateTransition1.setNode(label1);

        translateTransition1.setByX(0);

        translateTransition1.setToX(100);

        translateTransition1.setCycleCount(1);

        translateTransition1.setAutoReverse(false);

        translateTransition1.play();

        // Animation for label2
        TranslateTransition translateTransition2 = new TranslateTransition();

        translateTransition2.setDuration(Duration.millis(800));

        translateTransition2.setNode(label2);

        translateTransition2.setByX(0);

        translateTransition2.setToX(-256);

        translateTransition2.setCycleCount(1);

        translateTransition2.setAutoReverse(false);

        translateTransition2.play();

        // Animation for label3
        TranslateTransition translateTransition3 = new TranslateTransition();

        translateTransition3.setDuration(Duration.millis(800));

        translateTransition3.setNode(label3);

        translateTransition3.setByX(0);

        translateTransition3.setToX(100);

        translateTransition3.setCycleCount(1);

        translateTransition3.setAutoReverse(false);

        translateTransition3.play();

        // Animation for label4
        TranslateTransition translateTransition4 = new TranslateTransition();

        translateTransition4.setDuration(Duration.millis(800));

        translateTransition4.setNode(label4);

        translateTransition4.setByX(0);

        translateTransition4.setToX(-167);

        translateTransition4.setCycleCount(1);

        translateTransition4.setAutoReverse(false);

        translateTransition4.play();

        // Animation for label5
        TranslateTransition translateTransition5 = new TranslateTransition();

        translateTransition5.setDuration(Duration.millis(800));

        translateTransition5.setNode(label5);

        translateTransition5.setByX(0);

        translateTransition5.setToX(100);

        translateTransition5.setCycleCount(1);

        translateTransition5.setAutoReverse(false);

        translateTransition5.play();

        // Animation for label6
        TranslateTransition translateTransition6 = new TranslateTransition();

        translateTransition6.setDuration(Duration.millis(800));

        translateTransition6.setNode(label6);

        translateTransition6.setByX(0);

        translateTransition6.setToX(-171);

        translateTransition6.setCycleCount(1);

        translateTransition6.setAutoReverse(false);

        translateTransition6.play();

        label1.setOnMouseEntered(event -> {
            label1.setStyle("-fx-text-fill: #F08000;");
        });
        label1.setOnMouseExited(event -> {
            label1.setStyle("-fx-text-fill: white;");
        });

        label2.setOnMouseEntered(event -> {
            label2.setStyle("-fx-text-fill: #F08000;");
        });
        label2.setOnMouseExited(event -> {
            label2.setStyle("-fx-text-fill: white;");
        });

        label3.setOnMouseEntered(event -> {
            label3.setStyle("-fx-text-fill: #F08000;");
        });
        label3.setOnMouseExited(event -> {
            label3.setStyle("-fx-text-fill: white;");
        });

        label4.setOnMouseEntered(event -> {
            label4.setStyle("-fx-text-fill: #F08000;");
        });
        label4.setOnMouseExited(event -> {
            label4.setStyle("-fx-text-fill: white;");
        });

        label5.setOnMouseEntered(event -> {
            label5.setStyle("-fx-text-fill: #F08000;");
        });
        label5.setOnMouseExited(event -> {
            label5.setStyle("-fx-text-fill: white;");
        });

        label6.setOnMouseEntered(event -> {
            label6.setStyle("-fx-text-fill: #F08000;");
        });
        label6.setOnMouseExited(event -> {
            label6.setStyle("-fx-text-fill: white;");
        });
    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Home");

        stage.setScene(scene);

        stage.show();

    }
    public void clickOnLabel1() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learn/learn1.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Learn");

        stage.setScene(scene);

        stage.show();

    }
    public void clickOnLabel2() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learn/learn2.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Learn");

        stage.setScene(scene);

        stage.show();

    }
    public void clickOnLabel3() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learn/learn3.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Learn");

        stage.setScene(scene);

        stage.show();

    }
    public void clickOnLabel4() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learn/learn4.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Learn");

        stage.setScene(scene);

        stage.show();

    }
    public void clickOnLabel5() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learn/learn5.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Learn");

        stage.setScene(scene);

        stage.show();

    }
    public void clickOnLabel6() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learn/learn6.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Learn");

        stage.setScene(scene);

        stage.show();

    }
}
