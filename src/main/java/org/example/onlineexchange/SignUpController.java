package org.example.onlineexchange;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

public class SignUpController implements Initializable {

    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;
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
    private Label captchaLabel, enterCaptchaLabel, createAccountLabel, loginLabel;
    @FXML
    private ImageView recaptchaImageView, userIconImageView, lockIconImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Animation for welcome label
        TranslateTransition translateTransitionWelcome = new TranslateTransition();

        translateTransitionWelcome.setDuration(Duration.millis(1200));

        translateTransitionWelcome.setNode(createAccountLabel);

        translateTransitionWelcome.setByY(0);

        translateTransitionWelcome.setToY(60);

        translateTransitionWelcome.setCycleCount(1);

        translateTransitionWelcome.setAutoReverse(false);

        translateTransitionWelcome.play();

        // Animation for captcha label
        TranslateTransition translateTransitionCaptcha = new TranslateTransition();

        translateTransitionCaptcha.setDuration(Duration.millis(1200));

        translateTransitionCaptcha.setNode(enterCaptchaLabel);

        translateTransitionCaptcha.setByX(0);

        translateTransitionCaptcha.setToX(155);

        translateTransitionCaptcha.setCycleCount(1);

        translateTransitionCaptcha.setAutoReverse(false);

        translateTransitionCaptcha.play();

        // Animation for user icon
        TranslateTransition translateTransitionUserIcon = new TranslateTransition();

        translateTransitionUserIcon.setDuration(Duration.millis(1200));

        translateTransitionUserIcon.setNode(userIconImageView);

        translateTransitionUserIcon.setByX(0);

        translateTransitionUserIcon.setToX(76);

        translateTransitionUserIcon.setCycleCount(1);

        translateTransitionUserIcon.setAutoReverse(false);

        translateTransitionUserIcon.play();

        // Animation for lock icon
        TranslateTransition translateTransitionLockIcon = new TranslateTransition();

        translateTransitionLockIcon.setDuration(Duration.millis(1200));

        translateTransitionLockIcon.setNode(lockIconImageView);

        translateTransitionLockIcon.setByX(0);

        translateTransitionLockIcon.setToX(76);

        translateTransitionLockIcon.setCycleCount(1);

        translateTransitionLockIcon.setAutoReverse(false);

        translateTransitionLockIcon.play();

        // Animation for login label
        TranslateTransition translateTransitionLoginLabel = new TranslateTransition();

        translateTransitionLoginLabel.setDuration(Duration.millis(1200));

        translateTransitionLoginLabel.setNode(loginLabel);

        translateTransitionLoginLabel.setByX(0);

        translateTransitionLoginLabel.setToX(277);

        translateTransitionLoginLabel.setCycleCount(1);

        translateTransitionLoginLabel.setAutoReverse(false);

        translateTransitionLoginLabel.play();

        realCaptcha = generateCaptcha();

        captchaLabel.setText(realCaptcha);

        recaptchaImageView.setOnMouseClicked(event -> {

            realCaptcha = generateCaptcha();

            captchaLabel.setText(realCaptcha);
        });

        loginLabel.setOnMouseEntered(event -> {
            loginLabel.setStyle("-fx-text-fill: #F08000;");
        });

        loginLabel.setOnMouseExited(event -> {
            loginLabel.setStyle("-fx-text-fill: white;");
        });

    }
    public void clickOnLoginLabel(MouseEvent mouseEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));

        stage = (Stage) loginLabel.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Login");

        stage.setScene(scene);

        stage.show();
    }
    public void signUp(ActionEvent event) throws SQLException, IOException {

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
//            System.out.println("User Signed up successfully!");

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();

            String query = "INSERT INTO users (username, firstName, lastName, password, email, phoneNumber, profile)" +
                            "VALUES (?, ?, ? ,?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, inputUsername);
            preparedStatement.setString(2, inputFirstName);
            preparedStatement.setString(3, inputLastName);
            preparedStatement.setString(4, inputPassword);
            preparedStatement.setString(5, inputEmail);
            preparedStatement.setString(6, inputPhoneNumber);
            preparedStatement.setString(7, "C:/Users/pc/Desktop/onlineExchange/src/main/resources/org/example/onlineexchange/userProfile.png");

            int addedRows = preparedStatement.executeUpdate();

            if (addedRows > 0) {
                logger.log(Level.INFO, inputUsername);
                logger.log(Level.INFO, inputFirstName);
                logger.log(Level.INFO, inputLastName);
                logger.log(Level.INFO, inputPassword);
                logger.log(Level.INFO, inputEmail);
                logger.log(Level.INFO, inputPhoneNumber);
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

            createWallet();

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

            Stage stage = (Stage) signUpBtn.getScene().getWindow();

            scene = new Scene(root);

            stage.setTitle("Main");

            stage.setScene(scene);

            stage.show();

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

    public void createWallet() {

        String query = "INSERT INTO assets (username, dollar, euro, toman, yen, pound) " +
                "VALUES(?, ?, ?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, inputUsername);
            pstmt.setString(2, "5.0");
            pstmt.setString(3, "0.0");
            pstmt.setString(4, "0.0");
            pstmt.setString(5, "0.0");
            pstmt.setString(6, "0.0");

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

    }
}
