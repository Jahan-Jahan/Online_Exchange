package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController implements Initializable {

    String inputUsername, inputFirstName, inputLastName, inputPassword,
            inputRepeatedPassword, inputPhoneNumber, inputEmail, inputCaptcha;
    String realCaptcha;

    @FXML
    private TextField usernameTextField, firstNameTextField, lastNameTextField,
            emailTextField, phoneTextField, captchaTextField;
    @FXML
    private PasswordField passwordField, repeatPasswordField;
    @FXML
    private Button signUpBtn;
    @FXML
    private Label captchaLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        realCaptcha = generateCaptcha();

        captchaLabel.setText(realCaptcha);

    }

    public void signUp(ActionEvent event) {

        inputUsername = usernameTextField.getText();

        inputFirstName = firstNameTextField.getText();

        inputLastName = lastNameTextField.getText();

        inputPassword = passwordField.getText();

        inputRepeatedPassword = repeatPasswordField.getText();

        inputEmail = emailTextField.getText();

        inputPhoneNumber = phoneTextField.getText();

        inputCaptcha = captchaTextField.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid input!");
        alert.setHeaderText("Sorry! you can't Sign up...");
        alert.setContentText("Username validation: " + usernameValidation(inputUsername) +
                "\nFirstName validation: " + nameValidation(inputFirstName) +
                "\nLastName validation: " + nameValidation(inputLastName) +
                "\nPassword validation: " + passwordValidation(inputPassword, inputRepeatedPassword) +
                "\nEmail validation: " + emailValidation(inputEmail) +
                "\nPhoneNumber validation: " + phoneValidation(inputPhoneNumber) +
                "\nCaptcha validation: " + captchaValidation(inputCaptcha));

        if (usernameValidation(inputUsername) && nameValidation(inputFirstName) &&
                nameValidation(inputLastName) && passwordValidation(inputPassword, inputRepeatedPassword) &&
                emailValidation(inputEmail) && phoneValidation(inputPhoneNumber) &&
                captchaValidation(inputCaptcha)) {
            System.out.println("User Signed up successfully!");
            System.out.println(inputUsername);
            System.out.println(inputFirstName);
            System.out.println(inputLastName);
            System.out.println(inputPassword);
            System.out.println(inputEmail);
            System.out.println(inputPhoneNumber);
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
    public boolean passwordValidation(String unvalidatedPassword1, String unvalidatedPassword2) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9!@#$%^&*_-]{8,20}");
        Matcher matcher = pattern.matcher(unvalidatedPassword1);
        return matcher.find() && unvalidatedPassword1.equals(unvalidatedPassword2);
    }
    public boolean nameValidation(String unvalidatedName) {
        Pattern pattern = Pattern.compile("[A-Za-z]{3,20}");
        Matcher matcher = pattern.matcher(unvalidatedName);
        return matcher.find();
    }
    public boolean phoneValidation(String unvalidatedPhone) {
        Pattern pattern = Pattern.compile("[0-9]{11}");
        Matcher matcher = pattern.matcher(unvalidatedPhone);
        return matcher.find();
    }
    public boolean emailValidation(String unvalidatedEmail) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(unvalidatedEmail);
        return matcher.find();
    }
}
