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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private SignUpController signUpObject = new SignUpController();
    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    private String username, newUsername, email, newEmail, phone, newPhone;

    @FXML
    private Button backBtn, addMoney;
    @FXML
    private Label usernameLabel, emailLabel, phoneLabel, editLabel1,
            editLabel2, editLabel3, assetsLabel, exchangeLabel;
    @FXML
    private ImageView profileImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username = LoginController.getUsername();

        editLabel1.setOnMouseEntered(event -> {
            editLabel1.setStyle("-fx-text-fill: #0437F2;");
        });
        editLabel1.setOnMouseExited(event -> {
            editLabel1.setStyle("-fx-text-fill: white;");
        });

        editLabel2.setOnMouseEntered(event -> {
            editLabel2.setStyle("-fx-text-fill: #0437F2;");
        });
        editLabel2.setOnMouseExited(event -> {
            editLabel2.setStyle("-fx-text-fill: white;");
        });

        editLabel3.setOnMouseEntered(event -> {
            editLabel3.setStyle("-fx-text-fill: #0437F2;");
        });
        editLabel3.setOnMouseExited(event -> {
            editLabel3.setStyle("-fx-text-fill: white;");
        });

        exchangeLabel.setOnMouseEntered(event -> {
            exchangeLabel.setStyle("-fx-text-fill: #0437F2;");
        });
        exchangeLabel.setOnMouseExited(event -> {
            exchangeLabel.setStyle("-fx-text-fill: white;");
        });

        // Animation for username label
        TranslateTransition translateTransitionUsername = new TranslateTransition();

        translateTransitionUsername.setDuration(Duration.millis(1200));

        translateTransitionUsername.setNode(usernameLabel);

        translateTransitionUsername.setByX(0);

        translateTransitionUsername.setToX(100);

        translateTransitionUsername.setCycleCount(1);

        translateTransitionUsername.setAutoReverse(false);

        translateTransitionUsername.play();

        // Animation for email label
        TranslateTransition translateTransitionEmail = new TranslateTransition();

        translateTransitionEmail.setDuration(Duration.millis(1200));

        translateTransitionEmail.setNode(emailLabel);

        translateTransitionEmail.setByX(0);

        translateTransitionEmail.setToX(100);

        translateTransitionEmail.setCycleCount(1);

        translateTransitionEmail.setAutoReverse(false);

        translateTransitionEmail.play();

        // Animation for phone label
        TranslateTransition translateTransitionPhone = new TranslateTransition();

        translateTransitionPhone.setDuration(Duration.millis(1200));

        translateTransitionPhone.setNode(phoneLabel);

        translateTransitionPhone.setByX(0);

        translateTransitionPhone.setToX(100);

        translateTransitionPhone.setCycleCount(1);

        translateTransitionPhone.setAutoReverse(false);

        translateTransitionPhone.play();

        // Animation assets label
        TranslateTransition translateTransitionAssets = new TranslateTransition();

        translateTransitionAssets.setDuration(Duration.millis(1200));

        translateTransitionAssets.setNode(assetsLabel);

        translateTransitionAssets.setByX(0);

        translateTransitionAssets.setToX(100);

        translateTransitionAssets.setCycleCount(1);

        translateTransitionAssets.setAutoReverse(false);

        translateTransitionAssets.play();

        initializeLabels(username);

    }
    public void initializeLabels(String currentUsername) {

        String updateQuery = "SELECT email, phoneNumber FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, currentUsername);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                email = resultSet.getString("email");
                phone = resultSet.getString("phoneNumber");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        usernameLabel.setText(currentUsername);

        emailLabel.setText(email);

        phoneLabel.setText(phone);

        LoginController.setUsername(currentUsername);

    }
    public void clickOnEdit1() throws SQLException, IOException {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Change username");
        dialog.setHeaderText("New Username");
        dialog.setContentText("New username:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            newUsername = result.get();

            if (signUpObject.usernameValidation(newUsername)) {

                changeUsername();

                initializeLabels(newUsername);

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Validation");
                alert.setHeaderText("About the new username...");
                alert.setContentText("Please Enter valid username!");

                alert.show();

            }
        }

    }
    public void clickOnEdit2() throws SQLException, IOException {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Change email");
        dialog.setHeaderText("New Email");
        dialog.setContentText("New email:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            newEmail = result.get();

            if (signUpObject.emailValidation(newEmail)) {

                changeEmail();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Validation");
                alert.setHeaderText("About the new email...");
                alert.setContentText("Please Enter valid email!");

                alert.show();
            }
        }

    }
    public void clickOnEdit3() throws SQLException, IOException {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Change phone");
        dialog.setHeaderText("New Phone");
        dialog.setContentText("New phone:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            newPhone = result.get();

            if (signUpObject.phoneValidation(newPhone)) {

                changePhone();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Validation");
                alert.setHeaderText("About the new phone number...");
                alert.setContentText("Please Enter valid phone number!");

                alert.show();
            }
        }

    }
    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }
    public void changeUsername() {

        if (signUpObject.usernameValidation(newUsername)) {

            String updateQuery = "UPDATE users SET username = ? WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, username);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Username updated successfully.");
                } else {
                    System.out.println("Failed to update username. User not found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Changing username");

            alert.setHeaderText("Validation username...");

            alert.setContentText("Please enter a valid username!");

            alert.show();

        }

    }
    public void changeEmail() throws IOException, SQLException {

        if (signUpObject.emailValidation(newEmail)) {

            String updateQuery = "UPDATE users SET email = ? WHERE email = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                pstmt.setString(1, newEmail);
                pstmt.setString(2, email);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Email updated successfully.");
                } else {
                    System.out.println("Failed to update email. User not found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Changing email");

            alert.setHeaderText("Validation email...");

            alert.setContentText("Please enter a valid email!");

            alert.show();

        }

    }
    public void changePhone() throws IOException, SQLException {

        if (signUpObject.phoneValidation(newPhone)) {

            String updateQuery = "UPDATE users SET phoneNumber = ? WHERE phoneNumber = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                pstmt.setString(1, newPhone);
                pstmt.setString(2, phone);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Phone updated successfully.");
                } else {
                    System.out.println("Failed to update phone. User not found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Changing phone");

            alert.setHeaderText("Validation phone...");

            alert.setContentText("Please enter a valid phone!");

            alert.show();

        }

    }
    public void goToExchangePage() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("exchange/exchange.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Exchange");

        stage.setScene(scene);

        stage.show();

    }
    public void addMoney(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Add money");
        dialog.setHeaderText("How much money do you want to add?");
        dialog.setContentText("Enter here:");

        Optional<String> result = dialog.showAndWait();

        double moneyToAdd = 0;

        if (result.isPresent()) {

            moneyToAdd = Double.parseDouble(result.get());

        }

        String assetsLabelString = assetsLabel.getText();
        assetsLabelString = assetsLabelString.split(":")[1].strip();
        assetsLabelString = assetsLabelString.substring(0, assetsLabelString.length() - 1);

        System.out.println(assetsLabelString);

        double value = Double.parseDouble(assetsLabelString);
        value += moneyToAdd;

        assetsLabel.setText("Your Assets: " + String.valueOf(value) + "$");


        // add the money to the database



    }

    public void clickOnProfile() {

        stage = (Stage) backBtn.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            profileImageView.setImage(image);
        }

        String query = "UPDATE users SET profile = ? WHERE username = ?";

        String profileToSet = "";
        if (file != null) {
            profileToSet = file.toURI().toString().substring(6);
        }

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, profileToSet);
            pstmt.setString(2, LoginController.getUsername());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully.");
            } else {
                System.out.println("Failed to update Profile. User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}