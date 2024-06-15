package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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
    private Label captchaLabel, signUpLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        realCaptcha = generateCaptcha();

        captchaLabel.setText(realCaptcha);

        signUpLabel.setOnMouseEntered(event -> {
            signUpLabel.setStyle("-fx-text-fill: #F08000;");
        });

        signUpLabel.setOnMouseExited(event -> {
            signUpLabel.setStyle("-fx-text-fill: white;");
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