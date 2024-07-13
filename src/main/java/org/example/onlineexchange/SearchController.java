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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchController implements Initializable {

    private static final Logger logger = Logger.getLogger(HistoryController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Abolfazl_84";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private ListView<SearchResult> resultListView;
    @FXML
    private TextField inputTextField;
    @FXML
    private Button backBtn;

    private ObservableList<SearchResult> searchResultList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        searchResultList = FXCollections.observableArrayList();

        resultListView.setItems(searchResultList);

        resultListView.setCellFactory(param -> new SearchCell(this));

    }

    public void addSearchResult(SearchResult search) {
        searchResultList.add(search);
    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }

    public void onEnter(ActionEvent event) {

        resultListView.getItems().clear();

        String query = "SELECT username, firstName, lastName, email FROM users;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String input = inputTextField.getText().toLowerCase();
                    if (rs.getString("username").toLowerCase().contains(input) ||
                            rs.getString("firstName").toLowerCase().contains(input) ||
                            rs.getString("lastName").toLowerCase().contains(input) ||
                            rs.getString("email").toLowerCase().contains(input)) {

                        SearchResult resultToAdd = new SearchResult(
                                rs.getString("username") + "-" +
                                        rs.getString("firstName") + "-" +
                                        rs.getString("lastName") + "-" +
                                        rs.getString("email")
                                );

                        boolean status = true;

                        for (SearchResult sr : searchResultList) {
                            if (sr.username.equals(resultToAdd.username)) {
                                status = false;
                                break;
                            }
                        }
                        if (status) {
                            searchResultList.add(resultToAdd);
                        }
                    }
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from the table.");
        }

    }

    public static class SearchResult {
        private String username;

        public SearchResult(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

    }
}
