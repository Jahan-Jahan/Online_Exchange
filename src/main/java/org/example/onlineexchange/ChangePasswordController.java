package org.example.onlineexchange;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordController implements Initializable {

    private static final Logger logger = Logger.getLogger(ChangePasswordController.class.getName());

    private static final String URL = "jdbc:mysql://localhost:3306/crypto";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;
    private String inputPassword, inputRepeatPassword, email;

    @FXML
    private Label textLabel;
    @FXML
    private Button submitBtn;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        email = ForgotPasswordController.getEmail();

        // Animation for text label
        TranslateTransition translateTransitionTextLabel = new TranslateTransition();

        translateTransitionTextLabel.setDuration(Duration.millis(1200));

        translateTransitionTextLabel.setNode(textLabel);

        translateTransitionTextLabel.setByY(0);

        translateTransitionTextLabel.setToY(62);

        translateTransitionTextLabel.setCycleCount(1);

        translateTransitionTextLabel.setAutoReverse(false);

        translateTransitionTextLabel.play();

    }
    public void changePassword(ActionEvent event) throws IOException, SQLException {

        inputPassword = passwordField.getText();

        inputRepeatPassword = repeatPasswordField.getText();

        if (passwordValidation(inputPassword, inputRepeatPassword)) {

            changeUserPassword(email, inputPassword);

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));

            stage = (Stage) submitBtn.getScene().getWindow();

            scene = new Scene(root);

            stage.setTitle("Login");

            stage.setScene(scene);

            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Changing password");

            alert.setHeaderText("Validation password...");

            alert.setContentText("Please enter a valid password!");

            alert.show();

            passwordField.setText("");

            repeatPasswordField.setText("");
        }

    }
    public boolean passwordValidation(String unvalidatedPassword1,String unvalidatedPassword2) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9!@#$%^&*_-]{8,20}");
        Matcher matcher = pattern.matcher(unvalidatedPassword1);
        return matcher.find() && unvalidatedPassword1.equals(unvalidatedPassword2);
    }
    private static void changeUserPassword(String userEmail, String newPassword) {

        String updateQuery = "UPDATE users SET password = ? WHERE email = ?";

        // Establish connection
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            // Set parameters
            pstmt.setString(1, newPassword); // Set the new password
            pstmt.setString(2, userEmail);    // Set the username

            // Execute update
            int rowsAffected = pstmt.executeUpdate();

            // Check if update was successful
            if (rowsAffected > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }
    }

    public void onEnter(ActionEvent event) throws IOException, SQLException { changePassword(event); }
}
