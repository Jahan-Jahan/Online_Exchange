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
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OffersController implements Initializable {

    private static final Logger logger = Logger.getLogger(OffersController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private ListView<Offer> offersListView;
    @FXML
    private Button backBtn;

    private ObservableList<Offer> offerList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        offerList = FXCollections.observableArrayList();

        offersListView.setItems(offerList);

        offersListView.setCellFactory(param -> new OfferCell(this));

        String query = "SELECT * FROM offers;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    addOffer(new Offer(rs.getString(2) + "-" + rs.getString(3) + "-" +
                            rs.getString(4), "Exchange " + rs.getString(5) + " " +
                            rs.getString(3) + ", you pay " + rs.getString(4), rs.getInt(1)));
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

    }

    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }

    public void addOffer(Offer offer) {
        offerList.add(offer);
    }

    public void removeOffer(Offer offer) {
        offerList.remove(offer);
    }

    public static class Offer {
        private String title;
        private String description;
        private int id;

        public Offer(String title, String description, int id) {
            this.title = title;
            this.description = description;
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }
    }
}
