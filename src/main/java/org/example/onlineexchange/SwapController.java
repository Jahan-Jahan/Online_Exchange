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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwapController implements Initializable {

    private static final Logger logger = Logger.getLogger(SwapController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn, swapBtn;
    @FXML
    private ChoiceBox<String> srcChoiceBox, desChoiceBox;
    @FXML
    private ImageView exchangeImageView, coin1, coin2, coin3;
    @FXML
    private TextField textField1;

    private String srcExchange = "dollar", desExchange = "euro";

    private double exchangeTax;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

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

        String[] options = {"dollar", "toman", "euro", "yen", "pound"};
        ObservableList<String> items = FXCollections.observableArrayList(options);

        srcChoiceBox.setItems(items);

        srcChoiceBox.setValue("dollar");

        srcChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            srcExchange = items.get(newValue.intValue());

        });

        desChoiceBox.setItems(items);

        desChoiceBox.setValue("dollar");

        desChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            desExchange = items.get(newValue.intValue());

        });

    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }

    public synchronized void swap(ActionEvent event) {

        double dollarPrice = MainPageController.dollarPrice;
        double euroPrice = MainPageController.euroPrice;
        double tomanPrice = MainPageController.tomanPrice;
        double yenPrice = MainPageController.yenPrice;
        double poundPrice = MainPageController.poundPrice;

        double srcPrice = Double.parseDouble(textField1.getText());

        double srcAssets = 0;
        double desAssets = 0;

        double result = 0;

        switch (srcExchange) {
            case "dollar":
                switch (desExchange) {
                    case "dollar":
                        break;
                    case "euro":
                        result = srcPrice * euroPrice;
                        break;
                    case "toman":
                        result = srcPrice * tomanPrice;
                        break;
                    case "yen":
                        result = srcPrice * yenPrice;
                        break;
                    case "pound":
                        result = srcPrice * poundPrice;
                        break;
                }
                break;
            case "euro":
                switch (desExchange) {
                    case "dollar":
                        result = (srcPrice / euroPrice) * dollarPrice;
                        break;
                    case "euro":
                        break;
                    case "toman":
                        result = (srcPrice / euroPrice) * tomanPrice;
                        break;
                    case "yen":
                        result = (srcPrice / euroPrice) * yenPrice;
                        break;
                    case "pound":
                        result = (srcPrice / euroPrice) * poundPrice;
                        break;
                }
                break;
            case "toman":
                switch (desExchange) {
                    case "dollar":
                        result = (srcPrice / tomanPrice) * dollarPrice;
                        break;
                    case "euro":
                        result = (srcPrice / tomanPrice) * euroPrice;
                        break;
                    case "toman":
                        break;
                    case "yen":
                        result = (srcPrice / tomanPrice) * yenPrice;
                        break;
                    case "pound":
                        result = (srcPrice / tomanPrice) * poundPrice;
                        break;
                }
                break;
            case "yen":
                switch (desExchange) {
                    case "dollar":
                        result = (srcPrice / yenPrice) * dollarPrice;
                        break;
                    case "euro":
                        result = (srcPrice / yenPrice) * euroPrice;
                        break;
                    case "toman":
                        result = (srcPrice / yenPrice) * tomanPrice;
                        break;
                    case "yen":
                        break;
                    case "pound":
                        result = (srcPrice / yenPrice) * poundPrice;
                        break;
                }
                break;
            case "pound":
                switch (desExchange) {
                    case "dollar":
                        result = (srcPrice / poundPrice) * dollarPrice;
                        break;
                    case "euro":
                        result = (srcPrice / poundPrice) * euroPrice;
                        break;
                    case "toman":
                        result = (srcPrice / poundPrice) * tomanPrice;
                        break;
                    case "yen":
                        result = (srcPrice / poundPrice) * yenPrice;
                        break;
                    case "pound":
                        break;
                }
                break;
        }

        String query = "SELECT " + srcExchange + ", " + desExchange + " FROM assets WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, LoginController.getUsername());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    srcAssets = rs.getDouble(srcExchange);
                    desAssets = rs.getDouble(desExchange);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Sorry!");
        alert.setHeaderText(null);
        alert.setContentText("You have not enough money!");

        if (srcAssets - srcPrice <= 0) {
            alert.showAndWait();
            return;
        }

        exchangeTax = srcPrice * 0.09;
        srcAssets -= (srcPrice + exchangeTax);
        desAssets += result;

        query = "UPDATE assets SET " + srcExchange + " = ?, " + desExchange + " = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, srcAssets);
            pstmt.setDouble(2, desAssets);
            pstmt.setString(3, LoginController.getUsername());

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

        double adminPart = 0;
        query = "SELECT " + srcExchange + " FROM assets WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "admin");

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    adminPart = rs.getDouble(srcExchange);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        adminPart += exchangeTax;

        query = "UPDATE assets SET " + srcExchange + " = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, adminPart);
            pstmt.setString(2, "admin");

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

        textField1.setText("");

    }
}
