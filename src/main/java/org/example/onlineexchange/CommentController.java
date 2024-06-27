package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CommentController implements Initializable {

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Abolfazl_84";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextArea commentTextArea;
    @FXML
    private Button submitBtn, backBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
    public void backToHome(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Home");

        stage.setScene(scene);

        stage.show();

    }
    public void getComment(ActionEvent event) throws SQLException {

        String username = LoginController.getUsername();

        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String query = "INSERT INTO comments (username, comment)" +
                "VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, commentTextArea.getText());

        int addedRows = preparedStatement.executeUpdate();

        if (addedRows == 1) {

            System.out.println("Commented!");

            commentTextArea.setText("");

        } else {
            System.out.println("There is a problem for commenting...");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Comment");

        alert.setHeaderText("Thank you for your comment!");

        alert.setContentText("Your comment receive to us successfully!");

        alert.show();

    }
}
