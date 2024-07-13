package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlogController implements Initializable {

    private static final Logger logger = Logger.getLogger(SwapController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn, submitBtn;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea textArea;
    @FXML
    private Label blogLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        blogLabel.setOnMouseEntered(e -> {
            blogLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.75), 10, 0.5, 0, 0);" +
                                "-fx-text-fill: gold;");
        });
        blogLabel.setOnMouseExited(e -> {
            blogLabel.setStyle("-fx-effect: none;" +
                                "-fx-text-fill: white;");
        });

    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }

    public void submit(ActionEvent event) {

        ZonedDateTime time = ZonedDateTime.now();

        String year = String.valueOf(time.getYear());
        String month = String.valueOf(time.getMonthValue());
        String day = String.valueOf(time.getDayOfMonth());
        String hour = String.valueOf(time.getHour());
        String min = String.valueOf(time.getMinute());
        String sec = String.valueOf(time.getSecond());

        if (month.length() == 1) month = "0" + month;
        if (day.length() == 1) day = "0" + day;
        if (hour.length() == 1) hour = "0" + hour;
        if (min.length() == 1) min = "0" + min;
        if (sec.length() == 1) sec = "0" + sec;

        String stringTime = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;

        String query = "INSERT INTO blog (time, username, title, text) VALUES(?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, stringTime);
            pstmt.setString(2, LoginController.getUsername());
            pstmt.setString(3, titleTextField.getText());
            pstmt.setString(4, textArea.getText());

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
                titleTextField.setText("");
                textArea.setText("");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

    }

    public void clickOnBlog() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("showBlog/show.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("User-Blog");

        stage.setScene(scene);

        stage.show();

    }
}
