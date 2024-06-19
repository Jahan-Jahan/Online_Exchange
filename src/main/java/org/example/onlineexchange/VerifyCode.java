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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class VerifyCode implements Initializable {

    private Parent currentRoot, root;
    private Stage currentStage, stage;
    private Scene scene;

    String inputCode , generatedCode;

    @FXML
    private Label textLabel;
    @FXML
    private TextField codeTextField;
    @FXML
    private Button submitBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generatedCode = ForgotPasswordController.getGeneratedCode();

        // Animation for text label
        TranslateTransition translateTransitionTextLabel = new TranslateTransition();

        translateTransitionTextLabel.setDuration(Duration.millis(1200));

        translateTransitionTextLabel.setNode(textLabel);

        translateTransitionTextLabel.setByY(0);

        translateTransitionTextLabel.setToY(73);

        translateTransitionTextLabel.setCycleCount(1);

        translateTransitionTextLabel.setAutoReverse(false);

        translateTransitionTextLabel.play();
    }
    public void submitCode(ActionEvent event) throws IOException {

        inputCode = codeTextField.getText();

        if (Objects.equals(generatedCode, inputCode)) {

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("changePassword/changePassword.fxml")));

            stage = (Stage) submitBtn.getScene().getWindow();

            scene = new Scene(root);

            stage.setTitle("Change password");

            stage.setScene(scene);

            stage.show();


        } else {

            try {
                currentRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("verifyCode/verifyCode.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            currentStage = (Stage) submitBtn.getScene().getWindow();

            currentStage.close();

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Wrong code!");

            alert.setHeaderText("This code is not valid...");

            alert.setContentText("Sorry! You can't change the password...\nBut you can try again!");

            alert.show();
        }
    }
}
