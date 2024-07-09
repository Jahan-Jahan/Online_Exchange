package org.example.onlineexchange;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private ListView<History> historyListView;
    @FXML
    private Button backBtn;

    private ObservableList<History> historyList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        historyList = FXCollections.observableArrayList();

        historyListView.setItems(historyList);

        historyListView.setCellFactory(param -> new HistoryCell(this));

        String query = "SELECT * FROM history ORDER BY id DESC;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String description = "At " + rs.getString("time") + "-id: " +
                            rs.getString("id") + "-" + rs.getString("buyer") +
                            " bought " + rs.getString("price") + " " + rs.getString("src") +
                            " from " + rs.getString("seller") + " for pay " + rs.getString("des");
                    addHistory(new History(description, rs.getInt("id")));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addHistory(HistoryController.History history) {
        historyList.add(history);
    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }

    public static class History {
        private String description;
        private int id;

        public History(String description, int id) {
            this.description = description;
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }
    }
}
