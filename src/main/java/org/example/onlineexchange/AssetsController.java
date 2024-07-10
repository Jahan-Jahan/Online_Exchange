package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssetsController implements Initializable {

    private static final Logger logger = Logger.getLogger(AssetsController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Abolfazl_84";

    private Parent root;
    private Stage stage;
    private Scene scene;

    double dollar, euro, toman, yen, pound;

    @FXML
    public Label dollarAssetsLabel, euroAssetsLabel, tomanAssetsLabel, yenAssetsLabel, poundAssetsLabel, totalLabel;
    @FXML
    private Button backBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateLabel();

    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profile/profile.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Profile");

        stage.setScene(scene);

        stage.show();

    }

    public void updateLabel() {

        String query = "SELECT dollar, euro, toman, yen, pound FROM assets WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, LoginController.getUsername());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    dollar = rs.getDouble("dollar");
                    euro = rs.getDouble("euro");
                    toman = rs.getDouble("toman");
                    yen = rs.getDouble("yen");
                    pound = rs.getDouble("pound");
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        dollarAssetsLabel.setText(String.format("%.2f", dollar));
        euroAssetsLabel.setText(String.format("%.2f", euro));
        tomanAssetsLabel.setText(String.format("%.2f", toman));
        yenAssetsLabel.setText(String.format("%.2f", yen));
        poundAssetsLabel.setText(String.format("%.2f", pound));

        double euroPrice = MainPageController.euroPrice;
        double tomanPrice = MainPageController.tomanPrice;
        double yenPrice = MainPageController.yenPrice;
        double poundPrice = MainPageController.poundPrice;

        double total = dollar + (euro / euroPrice) + (toman / tomanPrice) + (yen / yenPrice) + (pound / poundPrice);

        String formatted = String.format("%.2f", total);

        totalLabel.setText(formatted + "$");

    }

}
