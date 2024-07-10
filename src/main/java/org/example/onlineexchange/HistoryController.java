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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryController implements Initializable {

    private static final Logger logger = Logger.getLogger(HistoryController.class.getName());

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
            logger.log(Level.SEVERE, "An error occur in reading data from the table.");
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

    public void export(ActionEvent event) {

        String output = "C:/Users/pc/Desktop/onlineExchange/src/main/" +
                "resources/org/example/onlineexchange/history.csv";

        String query = "SELECT * FROM history ORDER BY id DESC;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery();
             BufferedWriter bw = new BufferedWriter(new FileWriter(output))) {

            int columnCount = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                bw.write(rs.getMetaData().getColumnName(i));
                if (i < columnCount) bw.write(",");
            }
            bw.newLine();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    bw.write(rs.getString(i));
                    if (i < columnCount) bw.write(",");
                }
                bw.newLine();
            }

            logger.log(Level.INFO, "Data has been written to {0}", output);


        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from the table.");
        } catch (IOException e) {
            logger.log(Level.INFO, "An error occur in writing data in the file.");
        }

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
