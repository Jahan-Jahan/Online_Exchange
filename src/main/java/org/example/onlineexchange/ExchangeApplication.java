package org.example.onlineexchange;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ExchangeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExchangeApplication.class.getResource("signUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        stage.getIcons().add(new Image("cryptoIcon.png"));
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}