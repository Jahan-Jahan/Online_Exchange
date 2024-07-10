package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransferController implements Initializable {

    private static final Logger logger = Logger.getLogger(TransferController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn;
    @FXML
    private TextField usernameTextField, priceTextField;

    private double exchangeTax;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }

    public synchronized void transfer(ActionEvent event) {

        String receiver = usernameTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());

        double senderDollar = 0;

        String query = "SELECT dollar FROM assets WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, LoginController.getUsername());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    senderDollar = rs.getDouble("dollar");
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Sorry!");
        alert.setHeaderText(null);
        alert.setContentText("You have not enough money!");

        if (senderDollar - price <= 0) {
            alert.show();
            return;
        }

        exchangeTax = price * 0.09;
        price -= price * 0.09;
        senderDollar -= price;

        query = "UPDATE assets SET dollar = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, senderDollar);
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

        double adminPart = 0;
        query = "SELECT dollar FROM assets WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "admin");

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    adminPart = rs.getDouble("dollar");
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        adminPart += exchangeTax;

        query = "UPDATE assets SET dollar = ? WHERE username = ?";

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

        double receiverDollar = 0;
        query = "SELECT dollar FROM assets WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, receiver);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    receiverDollar = rs.getDouble("dollar");
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        receiverDollar += price;

        query = "UPDATE assets SET dollar = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, receiverDollar);
            pstmt.setString(2, receiver);

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

        usernameTextField.setText("");
        priceTextField.setText("");
    }
}
