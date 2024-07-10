package org.example.onlineexchange;

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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController implements Initializable {

    private static final Logger logger = Logger.getLogger(GameController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn, getMoneyBtn;
    @FXML
    private Label scoreLabel, congLabel1, congLabel2;
    @FXML
    private ImageView dogImageView;

    private int score = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dogImageView.setOnMouseExited(event -> {
            dogImageView.setStyle("-fx-effect: none;");
        });
        scoreLabel.setText("Score: " + score);
        congLabel1.setStyle("-fx-text-fill: gold;");
        congLabel2.setStyle("-fx-text-fill: gold;");

    }

    public void getMoney(ActionEvent event) {

        congLabel1.setVisible(false);
        congLabel2.setVisible(false);
        dogImageView.setStyle("-fx-effect: none;");

        double money = 0;

        String query = "SELECT dollar FROM assets WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, LoginController.getUsername());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    money = rs.getDouble("dollar");
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        query = "UPDATE assets SET dollar = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            double dollar = (double) score / 100;

            pstmt.setString(1, String.valueOf(money + dollar));
            pstmt.setString(2, LoginController.getUsername());

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

        score = 0;
        scoreLabel.setText("Score: " + score);

    }

    public void clickOnDog() {

        dogImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.75), 15, 0.5, 0, 0);");

        score++;

        if (score == 100) {
            congLabel1.setVisible(true);
            congLabel2.setVisible(true);
        }

        scoreLabel.setText("Score: " + score);

    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }
}
