package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class MainPageController implements Initializable {

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private ImageView usdIcon, euroIcon, tomanIcon, yenIcon, poundIcon, soundImageView, profileImageView;
    @FXML
    private Button backBtn, premiumBtn;
    @FXML
    private Label learnLabel, aboutLabel, commentLabel, donateLabel;
    @FXML
    private Label priceLabel1, priceLabel2, priceLabel3, priceLabel4, priceLabel5;
    @FXML
    private Label changeLabel1, changeLabel2, changeLabel3, changeLabel4, changeLabel5;
    @FXML
    private Label minLabel1, minLabel2, minLabel3, minLabel4, minLabel5;
    @FXML
    private Label maxLabel1, maxLabel2, maxLabel3, maxLabel4, maxLabel5;

    private boolean sound = false;
    private MusicController musicController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        musicController = new MusicController();

        if (LoginController.profileImage != null) {
            profileImageView.setImage(LoginController.profileImage);
        }

        initializeEffects();

        calculateDollar();
        calculateEuro();
        calculateToman();
        calculateYen();
        calculatePound();

    }

    private void initializeEffects() {

        usdIcon.setOnMouseEntered(event -> {
            usdIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 7, 0.5, 0, 0);");
        });
        usdIcon.setOnMouseExited(event -> {
            usdIcon.setStyle("-fx-effect: none;");
        });

        euroIcon.setOnMouseEntered(event -> {
            euroIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 7, 0.5, 0, 0);");
        });
        euroIcon.setOnMouseExited(event -> {
            euroIcon.setStyle("-fx-effect: none;");
        });

        tomanIcon.setOnMouseEntered(event -> {
            tomanIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 7, 0.5, 0, 0);");
        });
        tomanIcon.setOnMouseExited(event -> {
            tomanIcon.setStyle("-fx-effect: none;");

        });

        yenIcon.setOnMouseEntered(event -> {
            yenIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 7, 0.5, 0, 0);");
        });
        yenIcon.setOnMouseExited(event -> {
            yenIcon.setStyle("-fx-effect: none;");
        });

        poundIcon.setOnMouseEntered(event -> {
            poundIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 7, 0.5, 0, 0);");
        });
        poundIcon.setOnMouseExited(event -> {
            poundIcon.setStyle("-fx-effect: none;");
        });

        learnLabel.setOnMouseEntered(e -> {
            learnLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 15, 0.5, 0, 0);" +
                    "-fx-text-fill: white");
        });
        learnLabel.setOnMouseExited(e -> {
            learnLabel.setStyle("-fx-effect: none;" +
                    "-fx-text-fill: #848484");
        });

        aboutLabel.setOnMouseEntered(e -> {
            aboutLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 15, 0.5, 0, 0);" +
                    "-fx-text-fill: white");
        });
        aboutLabel.setOnMouseExited(e -> {
            aboutLabel.setStyle("-fx-effect: none;" +
                    "-fx-text-fill: #848484");
        });

        commentLabel.setOnMouseEntered(e -> {
            commentLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 15, 0.5, 0, 0);" +
                    "-fx-text-fill: white");
        });
        commentLabel.setOnMouseExited(e -> {
            commentLabel.setStyle("-fx-effect: none;" +
                    "-fx-text-fill: #848484");
        });

        donateLabel.setOnMouseEntered(e -> {
            donateLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 15, 0.5, 0, 0);" +
                    "-fx-text-fill: white");
        });
        donateLabel.setOnMouseExited(e -> {
            donateLabel.setStyle("-fx-effect: none;" +
                    "-fx-text-fill: #848484");
        });

        soundImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.75), 5, 0.5, 0, 0);");
        soundImageView.setOnMouseEntered(e -> {
            soundImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.75), 15, 0.5, 0, 0);");
        });
        soundImageView.setOnMouseExited(e -> {
            soundImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.75), 5, 0.5, 0, 0);");
        });

        profileImageView.setOnMouseEntered(event -> {
            profileImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(80,200,120,0.75), 7, 0.5, 0, 0);");
        });
        profileImageView.setOnMouseExited(event -> {
            profileImageView.setStyle("-fx-effect: none;");
        });

    }

    public void clickOnProfile() throws IOException {

        // Go to the profile page

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profile/profile.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("profile");

        stage.setScene(scene);

        stage.show();

    }

    public void clickOnSoundIcon() {

        if (sound) {
            sound = false;

            InputStream iconStream = getClass().getResourceAsStream("soundless.png");

            if (iconStream != null) {

                Image icon = new Image(iconStream);

                soundImageView.setImage(icon);
            }

            musicController.stopMusic();

        } else {
            sound = true;

            InputStream iconStream = getClass().getResourceAsStream("sound.png");

            if (iconStream != null) {

                Image icon = new Image(iconStream);

                soundImageView.setImage(icon);
            }

            musicController.playMusic();

        }

    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Login");

        stage.setScene(scene);

        stage.show();

    }

    public void getPremiumAccount(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Soon!");

        alert.setHeaderText("You want Premium account!");

        alert.setContentText("Coming soon! For a month you can get it for 30% off!");

        alert.show();

    }

    public void clickForLearn() throws IOException {

        // Go to the Learn page

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learn/learn.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Learn");

        stage.setScene(scene);

        stage.show();

    }

    public void clickForAbout() throws IOException {

        // Go to the About us page

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("about/about.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("About");

        stage.setScene(scene);

        stage.show();

    }

    public void clickForComment() throws IOException {

        // Go to the Comment page

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("comment/comment.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Comment");

        stage.setScene(scene);

        stage.show();

    }

    public void clickForDonate() {

        // Go to the Pay page

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Donate");

        alert.setHeaderText("Abolfazl Jahangir, the card number is:");

        alert.setContentText("6037-6975-1961-2466\nThank you!");

        alert.show();

    }

    public void clickOnDollar() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dollar/dollar.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Dollar");

        stage.setScene(scene);

        stage.show();

    }

    public void clickOnEuro() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("euro/euro.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Euro");

        stage.setScene(scene);

        stage.show();

    }

    public void clickOnToman() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("toman/toman.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Toman");

        stage.setScene(scene);

        stage.show();

    }

    public void clickOnYen() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("yen/yen.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Yen");

        stage.setScene(scene);

        stage.show();

    }

    public void clickOnPound() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pound/pound.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Pound");

        stage.setScene(scene);

        stage.show();

    }

    public void calculateDollar() {

        String columnName = "usd";

        ArrayList<Double> columnData = new ArrayList<>();

        String query = "SELECT " + columnName + " FROM data;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                double value = rs.getDouble(columnName);
                columnData.add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.reverse(columnData);

        Double v1 = columnData.getFirst();
        Double v2 = v1;

        for (Double number : columnData) {

            if (Double.compare(number, v1) != 0) {
                v2 = number;
                break;
            }

        }

        double changes = ((v2 - v1) * 100) / v1;
        Double max = Collections.max(columnData);
        Double min = Collections.min(columnData);

        String formattedValue = String.format("%.4f", changes);

        // add to the label
        priceLabel1.setText(String.valueOf(v1));
        changeLabel1.setText("%" + formattedValue);
        minLabel1.setText(String.valueOf(min));
        maxLabel1.setText(String.valueOf(max));

        if (changes < 0) {
            changeLabel1.setStyle("-fx-text-fill: red;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        } else if (changes > 0) {
            changeLabel1.setStyle("-fx-text-fill: green;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        }

    }

    public void calculateEuro() {

        String columnName = "eur";

        ArrayList<Double> columnData = new ArrayList<>();

        String query = "SELECT " + columnName + " FROM data;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                double value = rs.getDouble(columnName);
                columnData.add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.reverse(columnData);

        Double v1 = columnData.getFirst();
        Double v2 = v1;

        for (Double number : columnData) {

            if (Double.compare(number, v1) != 0) {
                v2 = number;
                break;
            }

        }

        double changes = ((v2 - v1) * 100) / v1;
        Double max = Collections.max(columnData);
        Double min = Collections.min(columnData);

        String formattedValue = String.format("%.4f", changes);

        // add to the label
        priceLabel2.setText(String.valueOf(v1));
        changeLabel2.setText("%" + formattedValue);
        minLabel2.setText(String.valueOf(min));
        maxLabel2.setText(String.valueOf(max));

        if (changes < 0) {
            changeLabel2.setStyle("-fx-text-fill: red;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        } else if (changes > 0) {
            changeLabel2.setStyle("-fx-text-fill: green;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        }

    }

    public void calculateToman() {

        String columnName = "toman";

        ArrayList<Double> columnData = new ArrayList<>();

        String query = "SELECT " + columnName + " FROM data;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                double value = rs.getDouble(columnName);
                columnData.add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.reverse(columnData);

        Double v1 = columnData.getFirst();
        Double v2 = v1;

        for (Double number : columnData) {

            if (Double.compare(number, v1) != 0) {
                v2 = number;
                break;
            }

        }

        double changes = ((v2 - v1) * 100) / v1;
        Double max = Collections.max(columnData);
        Double min = Collections.min(columnData);

        String formattedValue = String.format("%.4f", changes);

        // add to the label
        priceLabel3.setText(String.valueOf(v1));
        changeLabel3.setText("%" + formattedValue);
        minLabel3.setText(String.valueOf(min));
        maxLabel3.setText(String.valueOf(max));

        if (changes < 0) {
            changeLabel3.setStyle("-fx-text-fill: red;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        } else if (changes > 0) {
            changeLabel3.setStyle("-fx-text-fill: green;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        }

    }

    public void calculateYen() {

        String columnName = "yen";

        ArrayList<Double> columnData = new ArrayList<>();

        String query = "SELECT " + columnName + " FROM data;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                double value = rs.getDouble(columnName);
                columnData.add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.reverse(columnData);

        Double v1 = columnData.getFirst();
        Double v2 = v1;

        for (Double number : columnData) {

            if (Double.compare(number, v1) != 0) {
                v2 = number;
                break;
            }

        }

        double changes = ((v2 - v1) * 100) / v1;
        Double max = Collections.max(columnData);
        Double min = Collections.min(columnData);

        String formattedValue = String.format("%.4f", changes);

        // add to the label
        priceLabel4.setText(String.valueOf(v1));
        changeLabel4.setText("%" + formattedValue);
        minLabel4.setText(String.valueOf(min));
        maxLabel4.setText(String.valueOf(max));

        if (changes < 0) {
            changeLabel4.setStyle("-fx-text-fill: red;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        } else if (changes > 0) {
            changeLabel4.setStyle("-fx-text-fill: green;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        }

    }

    public void calculatePound() {

        String columnName = "gbp";

        ArrayList<Double> columnData = new ArrayList<>();

        String query = "SELECT " + columnName + " FROM data;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                double value = rs.getDouble(columnName);
                columnData.add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.reverse(columnData);

        Double v1 = columnData.getFirst();
        Double v2 = v1;

        for (Double number : columnData) {

            if (Double.compare(number, v1) != 0) {
                v2 = number;
                break;
            }

        }

        double changes = ((v2 - v1) * 100) / v1;
        Double max = Collections.max(columnData);
        Double min = Collections.min(columnData);

        String formattedValue = String.format("%.4f", changes);

        // add to the label
        priceLabel5.setText(String.valueOf(v1));
        changeLabel5.setText("%" + formattedValue);
        minLabel5.setText(String.valueOf(min));
        maxLabel5.setText(String.valueOf(max));

        if (changes < 0) {
            changeLabel5.setStyle("-fx-text-fill: red;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        } else if (changes > 0) {
            changeLabel5.setStyle("-fx-text-fill: green;" +
                    "-fx-effect: dropshadow(gaussian, rgba(10,10,10,0.75), 7, 0.5, 0, 0);");
        }

    }
}