package org.example.onlineexchange;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    private final String databaseUrl = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    String realCaptcha;
    static String inputCaptcha, inputUsername, inputPassword;

    @FXML
    private TextField usernameTextField, captchaTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button submitBtn;
    @FXML
    private Label captchaLabel, enterCaptchaLabel, signUpLabel, welcomeLabel, forgotPasswordLabel;
    @FXML
    private ImageView recaptchaImageView, userIconImageView, lockIconImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Animation for welcome label
        TranslateTransition translateTransitionWelcome = new TranslateTransition();

        translateTransitionWelcome.setDuration(Duration.millis(1200));

        translateTransitionWelcome.setNode(welcomeLabel);

        translateTransitionWelcome.setByY(0);

        translateTransitionWelcome.setToY(44);

        translateTransitionWelcome.setCycleCount(1);

        translateTransitionWelcome.setAutoReverse(false);

        translateTransitionWelcome.play();

        // Animation for captcha label
        TranslateTransition translateTransitionCaptcha = new TranslateTransition();

        translateTransitionCaptcha.setDuration(Duration.millis(1200));

        translateTransitionCaptcha.setNode(enterCaptchaLabel);

        translateTransitionCaptcha.setByX(0);

        translateTransitionCaptcha.setToX(73);

        translateTransitionCaptcha.setCycleCount(1);

        translateTransitionCaptcha.setAutoReverse(false);

        translateTransitionCaptcha.play();

        // Animation for sign up label
        TranslateTransition translateTransitionSignUp = new TranslateTransition();

        translateTransitionSignUp.setDuration(Duration.millis(1200));

        translateTransitionSignUp.setNode(signUpLabel);

        translateTransitionSignUp.setByX(0);

        translateTransitionSignUp.setToX(125);

        translateTransitionSignUp.setCycleCount(1);

        translateTransitionSignUp.setAutoReverse(false);

        translateTransitionSignUp.play();

        // Animation for user icon
        TranslateTransition translateTransitionUserIcon = new TranslateTransition();

        translateTransitionUserIcon.setDuration(Duration.millis(1200));

        translateTransitionUserIcon.setNode(userIconImageView);

        translateTransitionUserIcon.setByX(0);

        translateTransitionUserIcon.setToX(121);

        translateTransitionUserIcon.setCycleCount(1);

        translateTransitionUserIcon.setAutoReverse(false);

        translateTransitionUserIcon.play();

        // Animation for lock icon
        TranslateTransition translateTransitionLockIcon = new TranslateTransition();

        translateTransitionLockIcon.setDuration(Duration.millis(1200));

        translateTransitionLockIcon.setNode(lockIconImageView);

        translateTransitionLockIcon.setByX(0);

        translateTransitionLockIcon.setToX(121);

        translateTransitionLockIcon.setCycleCount(1);

        translateTransitionLockIcon.setAutoReverse(false);

        translateTransitionLockIcon.play();

        // Animation for forgot label
        TranslateTransition translateTransitionForgotLabel = new TranslateTransition();

        translateTransitionForgotLabel.setDuration(Duration.millis(1200));

        translateTransitionForgotLabel.setNode(forgotPasswordLabel);

        translateTransitionForgotLabel.setByX(486);

        translateTransitionForgotLabel.setToX(-147); // 339

        translateTransitionForgotLabel.setCycleCount(1);

        translateTransitionForgotLabel.setAutoReverse(false);

        translateTransitionForgotLabel.play();

        realCaptcha = generateCaptcha();

        captchaLabel.setText(realCaptcha);

        signUpLabel.setOnMouseEntered(event -> {
            signUpLabel.setStyle("-fx-text-fill: #F08000;");
        });

        signUpLabel.setOnMouseExited(event -> {
            signUpLabel.setStyle("-fx-text-fill: white;");
        });

        forgotPasswordLabel.setOnMouseEntered(event -> {
            forgotPasswordLabel.setStyle("-fx-text-fill: #F08000;");
        });

        forgotPasswordLabel.setOnMouseExited(event -> {
            forgotPasswordLabel.setStyle("-fx-text-fill: white;");
        });

         recaptchaImageView.setOnMouseClicked(event -> {

            realCaptcha = generateCaptcha();

            captchaLabel.setText(realCaptcha);
        });
    }
    public void clickOnSignUpLabel(javafx.scene.input.MouseEvent mouseEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signUp/signUp.fxml")));

        stage = (Stage) signUpLabel.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Sign-up");

        stage.setScene(scene);

        stage.show();
    }

    public void clickOnForgotLabel(javafx.scene.input.MouseEvent mouseEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("forgotPassword/forgotPassword.fxml")));

        stage = (Stage) signUpLabel.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Forgot Password");

        stage.setScene(scene);

        stage.show();
    }

    public void login(ActionEvent event) throws SQLException, IOException {

        inputUsername = usernameTextField.getText();

        inputPassword = passwordPasswordField.getText();

        inputCaptcha = captchaTextField.getText();

        boolean accountExist = false;

        try (Connection connection = DriverManager.getConnection(databaseUrl, USERNAME, PASSWORD)) {
            if (connection != null) {
                if (doesUserExist(connection, inputUsername)) accountExist = true;
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid input!");
        alert.setHeaderText("Sorry! you can't login...");
        alert.setContentText("You have account: " + accountExist +
                        "\nUsername validation: " + usernameValidation(inputUsername) +
                        "\nPassword validation: " + passwordValidation(inputPassword) +
                        "\nCaptcha validation: " + captchaValidation(inputCaptcha));

        Alert createAccountAlert = new Alert(Alert.AlertType.CONFIRMATION);

        createAccountAlert.setTitle("Create Account!");

        createAccountAlert.setHeaderText("You don't have any account...");

        createAccountAlert.setContentText("Do you want to create an account?");

        if (accountExist && captchaValidation(inputCaptcha) && usernameValidation(inputUsername) && passwordValidation(inputPassword)) {
            System.out.println("User logged in successfully!");

            // go to the main page!

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

            scene = new Scene(root);

            stage = (Stage) submitBtn.getScene().getWindow();

            stage.setScene(scene);

            stage.setTitle("Main");

            stage.show();

        } else {

            if (!accountExist) {

                Optional<ButtonType> result = createAccountAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    System.out.println("Whoa! sign-up page!");


                    // change the scene to the sign-up


                } else {
                    alert.show();
                }

            } else {
                alert.show();
            }
        }
    }

    private static boolean doesUserExist(Connection connection, String username) {

        String query = "SELECT 1 FROM users WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();  // If there's a result, the user exists
            }
        } catch (SQLException e) {
            System.err.println("Query failed.");

            e.printStackTrace();

            return false;
        }
    }

    public String generateCaptcha() {
        StringBuilder code = new StringBuilder();

        for (int i = 48; i < 58; i++) code.append((char) i);

        for (int i = 65; i < 91; i++) code.append((char) i);

        for (int i = 97; i < 123; i++) code.append((char) i);

        StringBuilder captcha = new StringBuilder();

        for (int i = 0; i < 5; i++) {

            int index = (int) Math.round(Math.random() * code.length());

            captcha.append(code.charAt(index));
        }

        return captcha.toString();
    }
    public boolean captchaValidation(String unvalidatedCaptcha) {
        String allLowerRealCaptcha = realCaptcha.toLowerCase();
        String allLowerUnvalidatedCaptcha = unvalidatedCaptcha.toLowerCase();
        return allLowerRealCaptcha.equals(allLowerUnvalidatedCaptcha);
    }
    public boolean usernameValidation(String unvalidatedUsername) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9_@#$]{3,20}");
        Matcher matcher = pattern.matcher(unvalidatedUsername);
        return matcher.find();
    }
    public boolean passwordValidation(String unvalidatedPassword) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9!@#$%^&*_-]{8,20}");
        Matcher matcher = pattern.matcher(unvalidatedPassword);
        return matcher.find();
    }
    public static String getUsername() {
        return inputUsername;
    }
}