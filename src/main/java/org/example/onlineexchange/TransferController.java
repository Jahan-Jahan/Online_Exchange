package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
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
    @FXML
    private RadioButton dollarRadioBtn, tomanRadioBtn, euroRadioBtn, yenRadioBtn, poundRadioBtn;

    private ToggleGroup toggleGroup;

    private String choice;

    private double exchangeTax;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        toggleGroup = new ToggleGroup();

        dollarRadioBtn.setToggleGroup(toggleGroup);
        euroRadioBtn.setToggleGroup(toggleGroup);
        tomanRadioBtn.setToggleGroup(toggleGroup);
        yenRadioBtn.setToggleGroup(toggleGroup);
        poundRadioBtn.setToggleGroup(toggleGroup);

        dollarRadioBtn.setSelected(true);

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

        if (dollarRadioBtn.isSelected()) choice = "dollar";
        else if (tomanRadioBtn.isSelected()) choice = "toman";
        else if (euroRadioBtn.isSelected()) choice = "euro";
        else if (yenRadioBtn.isSelected()) choice = "yen";
        else if (poundRadioBtn.isSelected()) choice = "pound";

        String receiver = usernameTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());

        double senderMoney = 0;

        String query = "SELECT " + choice + " FROM assets WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, LoginController.getUsername());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    senderMoney = rs.getDouble(choice);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Sorry!");
        alert.setHeaderText(null);
        alert.setContentText("You have not enough money!");

        if (senderMoney - price <= 0) {
            alert.show();
            return;
        }

        exchangeTax = price * 0.09;
        senderMoney -= (price + exchangeTax);

        query = "UPDATE assets SET " + choice + " = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, senderMoney);
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
        query = "SELECT " + choice + " FROM assets WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "admin");

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    adminPart = rs.getDouble(choice);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        adminPart += exchangeTax;

        query = "UPDATE assets SET " + choice + " = ? WHERE username = ?";

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

        double receiverMoney = 0;
        query = "SELECT " + choice + " FROM assets WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, receiver);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    receiverMoney = rs.getDouble(choice);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        receiverMoney += price;

        query = "UPDATE assets SET " + choice + " = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, receiverMoney);
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
