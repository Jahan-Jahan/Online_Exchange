package org.example.onlineexchange;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {
    String realCaptcha;
    String inputCaptcha, inputUsername, inputPassword;

    @FXML
    private TextField usernameTextField, captchaTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button submitBtn;
    @FXML
    private Label captchaLabel, enterCaptchaLabel, signUpLabel, welcomeLabel;
    @FXML
    private ImageView recaptchaImageView, userIconImageView, lockIconImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Animation for welcome label
        TranslateTransition translateTransitionWelcome = new TranslateTransition();

        translateTransitionWelcome.setDuration(Duration.millis(1300));

        translateTransitionWelcome.setNode(welcomeLabel);

        translateTransitionWelcome.setByX(0);

        translateTransitionWelcome.setToX(239);

        translateTransitionWelcome.setCycleCount(1);

        translateTransitionWelcome.setAutoReverse(false);

        translateTransitionWelcome.play();

        // Animation for captcha label
        TranslateTransition translateTransitionCaptcha = new TranslateTransition();

        translateTransitionCaptcha.setDuration(Duration.millis(1300));

        translateTransitionCaptcha.setNode(enterCaptchaLabel);

        translateTransitionCaptcha.setByX(0);

        translateTransitionCaptcha.setToX(73);

        translateTransitionCaptcha.setCycleCount(1);

        translateTransitionCaptcha.setAutoReverse(false);

        translateTransitionCaptcha.play();

        // Animation for sign up label
        TranslateTransition translateTransitionSignUp = new TranslateTransition();

        translateTransitionSignUp.setDuration(Duration.millis(1300));

        translateTransitionSignUp.setNode(signUpLabel);

        translateTransitionSignUp.setByX(0);

        translateTransitionSignUp.setToX(232);

        translateTransitionSignUp.setCycleCount(1);

        translateTransitionSignUp.setAutoReverse(false);

        translateTransitionSignUp.play();

        // Animation for user icon
        TranslateTransition translateTransitionUserIcon = new TranslateTransition();

        translateTransitionUserIcon.setDuration(Duration.millis(1300));

        translateTransitionUserIcon.setNode(userIconImageView);

        translateTransitionUserIcon.setByX(0);

        translateTransitionUserIcon.setToX(121);

        translateTransitionUserIcon.setCycleCount(1);

        translateTransitionUserIcon.setAutoReverse(false);

        translateTransitionUserIcon.play();

        // Animation for lock icon
        TranslateTransition translateTransitionLockIcon = new TranslateTransition();

        translateTransitionLockIcon.setDuration(Duration.millis(1300));

        translateTransitionLockIcon.setNode(lockIconImageView);

        translateTransitionLockIcon.setByX(0);

        translateTransitionLockIcon.setToX(121);

        translateTransitionLockIcon.setCycleCount(1);

        translateTransitionLockIcon.setAutoReverse(false);

        translateTransitionLockIcon.play();

        realCaptcha = generateCaptcha();

        captchaLabel.setText(realCaptcha);

        signUpLabel.setOnMouseEntered(event -> {
            signUpLabel.setStyle("-fx-text-fill: #F08000;");
        });

        signUpLabel.setOnMouseExited(event -> {
            signUpLabel.setStyle("-fx-text-fill: white;");
        });

        recaptchaImageView.setOnMouseClicked(event -> {

            realCaptcha = generateCaptcha();

            captchaLabel.setText(realCaptcha);
        });
    }

    public void login(ActionEvent event) {

        inputUsername = usernameTextField.getText();

        inputPassword = passwordPasswordField.getText();

        inputCaptcha = captchaTextField.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid input!");
        alert.setHeaderText("Sorry! you can't login...");
        alert.setContentText("Username validation: " + usernameValidation(inputUsername) +
                        "\nPassword validation: " + passwordValidation(inputPassword) +
                        "\nCaptcha validation: " + captchaValidation(inputCaptcha));

        if (captchaValidation(inputCaptcha) && usernameValidation(inputUsername) && passwordValidation(inputPassword)) {
            System.out.println("User logged in successfully!");
            System.out.println(inputUsername);
            System.out.println(inputPassword);

            Stage stage = (Stage) submitBtn.getScene().getWindow();
            stage.close();
        } else {
            alert.show();
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
}