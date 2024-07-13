package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WithdrawController implements Initializable {

    private static final Logger logger = Logger.getLogger(WithdrawController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn;
    @FXML
    private TextField priceTextField;
    @FXML
    private RadioButton dollarRadioBtn, tomanRadioBtn, euroRadioBtn, yenRadioBtn, poundRadioBtn;

    private ToggleGroup toggleGroup;

    private String choice;

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

    public void addMoney(ActionEvent event) {

        if (dollarRadioBtn.isSelected()) choice = "dollar";
        else if (tomanRadioBtn.isSelected()) choice = "toman";
        else if (euroRadioBtn.isSelected()) choice = "euro";
        else if (yenRadioBtn.isSelected()) choice = "yen";
        else if (poundRadioBtn.isSelected()) choice = "pound";

        double total = 0;

        String query = "SELECT " + choice + " FROM assets WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, LoginController.getUsername());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    total += rs.getDouble(choice);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        total += Double.parseDouble(priceTextField.getText());

        query = "UPDATE assets SET " + choice + " = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(total));
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

        priceTextField.setText("");

    }

    public void onEnter(ActionEvent event) { addMoney(event); }
}
