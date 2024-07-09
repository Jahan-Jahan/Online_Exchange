package org.example.onlineexchange;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class HistoryCell extends ListCell<HistoryController.History> {

    private final HistoryController controller;

    @Override
    protected void updateItem(HistoryController.History history, boolean empty) {
        super.updateItem(history, empty);

        if (empty || history == null) {
            setText(null);
            setGraphic(null);
        } else {
            VBox vbox = new VBox();

            vbox.setStyle("-fx-background-color: #50C878;");

            Label descriptionLabel = new Label(history.getDescription());
            descriptionLabel.setWrapText(true);

            vbox.getChildren().addAll(descriptionLabel);

            setGraphic(vbox);
        }
    }

    public HistoryCell(HistoryController controller) {
        this.controller = controller;
    }

}
