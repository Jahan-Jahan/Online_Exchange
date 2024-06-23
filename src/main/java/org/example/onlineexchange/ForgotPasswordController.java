package org.example.onlineexchange;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class orgotPasswordController implements Initializable {

    private static String generatedCode;
    private static String inputEmail;

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label textLabel, invalidEmailLabel;
    @FXML
    private Button submitBtn;
    @FXML
    private TextField emailTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        invalidEmailLabel.setVisible(false);

        // Animation for text label
        TranslateTransition translateTransitionWelcome = new TranslateTransition();

        translateTransitionWelcome.setDuration(Duration.millis(1200));

        translateTransitionWelcome.setNode(textLabel);

        translateTransitionWelcome.setByY(0);

        translateTransitionWelcome.setToY(81);

        translateTransitionWelcome.setCycleCount(1);

        translateTransitionWelcome.setAutoReverse(false);

        translateTransitionWelcome.play();

    }
    public void sendEmail(ActionEvent event) throws IOException {

        generateCode();

        inputEmail = emailTextField.getText();

        System.out.println(getGeneratedCode());


        // The code for sending email

        if (emailValidation(inputEmail)) {

//            final String username = "mahdyalbajitest@gmail.com";
//            final String password = "mahdyalbaji0099";
//
//            Properties prop = new Properties();
//            prop.put("mail.smtp.host", "smtp.gmail.com");
//            prop.put("mail.smtp.port", "465");
//            prop.put("mail.smtp.auth", "true");
//            prop.put("mail.smtp.socketFactory.port", "465");
//            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//            Session session = Session.getInstance(prop,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(username, password);
//                        }
//                    });
//
//            try {

//
//                Message message = new MimeMessage(session);
//                message.setFrom(new InternetAddress("mahdyalbajitest@gmail.com"));
//                message.setRecipients(
//                        Message.RecipientType.TO,
//                        InternetAddress.parse(inputEmail)
//                );
//                message.setSubject("Testing Gmail SSL");
//                message.setText("Dear Mail Crawler,"
//                        + "\n\n Please do not spam my email!");
//
//                Transport.send(message);
//
//                System.out.println("Done");
//
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("verifyCode/verifyCode.fxml")));

            stage = (Stage) emailTextField.getScene().getWindow();

            scene = new Scene(root);

            stage.setScene(scene);

            stage.setTitle("Verify Code");

            stage.show();

        } else {

            invalidEmailLabel.setVisible(true);

        }

    }
    public void generateCode() {

        Random random = new Random();

        generatedCode = String.valueOf(random.nextInt(100000) + 10000);
    }
    public static String getGeneratedCode() {
        return generatedCode;
    }
    public static String getEmail() { return inputEmail; }
    public boolean emailValidation(String unvalidatedEmail) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(unvalidatedEmail);
        return matcher.find();
    }
}
