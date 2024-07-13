package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchCell extends ListCell<SearchController.SearchResult> {

    private static final Logger logger = Logger.getLogger(SearchCell.class.getName());

    private Parent root;
    private Stage stage;
    private Scene scene;

    private final SearchController controller;

    private VBox vbox;

    public static String resultSearchUsername;

    @Override
    protected void updateItem(SearchController.SearchResult search, boolean empty) {
        super.updateItem(search, empty);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        if (empty || search == null) {
            setText(null);
            setGraphic(null);
        } else {
            vbox = new VBox();

            vbox.setStyle("-fx-background-color: #BFA100;");

            Label descriptionLabel = new Label(search.getUsername());
            descriptionLabel.setFont(new Font("Verdana", 14));
            descriptionLabel.setStyle("-fx-text-fill: white;");
            descriptionLabel.setWrapText(true);

            Button viewBtn = getViewBtn(search);

            vbox.setOnMouseClicked(event -> {
                try {
                    handleViewProfile(search);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "An error occur in loading user blog.");
                }
            });

            vbox.getChildren().addAll(descriptionLabel, viewBtn);

            setGraphic(vbox);
        }
    }

    private Button getViewBtn(SearchController.SearchResult search) {
        Button viewBtn = new Button("view");

        viewBtn.setStyle("-fx-background-color: #50C878;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;");

        viewBtn.setOnMouseEntered(event -> {
            viewBtn.setStyle("-fx-effect: dropshadow(gaussian, rgba(250,250,250,0.75), 3, 0.5, 0, 0);" +
                            "-fx-background-color: #50C878;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 8;");
        });
        viewBtn.setOnMouseExited(event -> {
            viewBtn.setStyle("-fx-effect: none;" +
                            "-fx-background-color: #50C878;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 8;");
        });

        viewBtn.setOnAction(event -> {
            try {
                handleViewProfile(search);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "An error occur in loading user blog.");
            }
        });

        return viewBtn;
    }

    public SearchCell(SearchController controller) {
        this.controller = controller;
    }

    public void handleViewProfile(SearchController.SearchResult searchResult) throws IOException {

        resultSearchUsername = searchResult.getUsername();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("showBlog/show.fxml")));

        scene = new Scene(root);

        stage = (Stage) vbox.getScene().getWindow();

        stage.setTitle("User-Blog");

        stage.setScene(scene);

        stage.show();

    }

}
