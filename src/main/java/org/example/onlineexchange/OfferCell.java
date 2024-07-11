package org.example.onlineexchange;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OfferCell extends ListCell<OffersController.Offer> {

    private static final Logger logger = Logger.getLogger(OfferCell.class.getName());

    private final OffersController controller;

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private double exchangeTax;

    @Override
    protected void updateItem(OffersController.Offer offer, boolean empty) {

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        super.updateItem(offer, empty);

        if (empty || offer == null) {
            setText(null);
            setGraphic(null);
        } else {
            VBox vbox = new VBox();
            Label titleLabel = new Label(offer.getTitle());
            Label descriptionLabel = new Label(offer.getDescription());

            Button buyButton = new Button("Purchase");
            buyButton.setStyle("-fx-background-color: #50C878;" +
                                "-fx-text-fill: white;");
            buyButton.setOnAction(event -> handlePurchaseOffer(offer));

            String[] details = offer.getTitle().split("-");
            String seller = details[0];

            if (seller.equals(LoginController.getUsername())) {
                buyButton.setDisable(true);
            }

            vbox.getChildren().addAll(titleLabel, descriptionLabel, buyButton);

            setGraphic(vbox);
        }
    }

    public OfferCell(OffersController controller) {
        this.controller = controller;
    }

    private synchronized void handlePurchaseOffer(OffersController.Offer offer) {

        String[] details = offer.getTitle().split("-");
        String seller = details[0];
        String src = details[1];
        String des = details[2];

        details = offer.getDescription().split(" ");
        double price = Double.parseDouble(details[1]);

        double srcAssets = 0;
        double desAssets = 0;

        String query = "SELECT " + src + ", " + des + " FROM assets WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, LoginController.getUsername());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    srcAssets = rs.getDouble(src);
                    desAssets = rs.getDouble(des);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        double dollarPrice = MainPageController.dollarPrice;
        double euroPrice = MainPageController.euroPrice;
        double tomanPrice = MainPageController.tomanPrice;
        double yenPrice = MainPageController.yenPrice;
        double poundPrice = MainPageController.poundPrice;

        double result = 0;

        switch (src) {
            case "dollar":
                switch (des) {
                    case "dollar":
                        break;
                    case "euro":
                        result = price * euroPrice;
                        break;
                    case "toman":
                        result = price * tomanPrice;
                        break;
                    case "yen":
                        result = price * yenPrice;
                        break;
                    case "pound":
                        result = price * poundPrice;
                        break;
                }
                break;
            case "euro":
                switch (des) {
                    case "dollar":
                        result = (price / euroPrice) * dollarPrice;
                        break;
                    case "euro":
                        break;
                    case "toman":
                        result = (price / euroPrice) * tomanPrice;
                        break;
                    case "yen":
                        result = (price / euroPrice) * yenPrice;
                        break;
                    case "pound":
                        result = (price / euroPrice) * poundPrice;
                        break;
                }
                break;
            case "toman":
                switch (des) {
                    case "dollar":
                        result = (price / tomanPrice) * dollarPrice;
                        break;
                    case "euro":
                        result = (price / tomanPrice) * euroPrice;
                        break;
                    case "toman":
                        break;
                    case "yen":
                        result = (price / tomanPrice) * yenPrice;
                        break;
                    case "pound":
                        result = (price / tomanPrice) * poundPrice;
                        break;
                }
                break;
            case "yen":
                switch (des) {
                    case "dollar":
                        result = (price / yenPrice) * dollarPrice;
                        break;
                    case "euro":
                        result = (price / yenPrice) * euroPrice;
                        break;
                    case "toman":
                        result = (price / yenPrice) * tomanPrice;
                        break;
                    case "yen":
                        break;
                    case "pound":
                        result = (price / yenPrice) * poundPrice;
                        break;
                }
                break;
            case "pound":
                switch (des) {
                    case "dollar":
                        result = (price / poundPrice) * dollarPrice;
                        break;
                    case "euro":
                        result = (price / poundPrice) * euroPrice;
                        break;
                    case "toman":
                        result = (price / poundPrice) * tomanPrice;
                        break;
                    case "yen":
                        result = (price / poundPrice) * yenPrice;
                        break;
                    case "pound":
                        break;
                }
                break;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Sorry!");
        alert.setHeaderText(null);
        alert.setContentText("You have not enough money!");

        if (desAssets - result < 0) {
            alert.showAndWait();
            return;
        }

        exchangeTax = result * 0.09;
        srcAssets += price;
        desAssets -= result;

        query = "UPDATE assets SET " + src + " = ?, " + des + " = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(srcAssets));
            pstmt.setString(2, String.valueOf(desAssets));
            pstmt.setString(3, LoginController.getUsername());

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

        double srcSellerAssets = 0;
        double desSellerAssets = 0;

        query = "SELECT " + src + ", " + des + " FROM assets WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, seller);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    srcSellerAssets = rs.getDouble(src);
                    desSellerAssets = rs.getDouble(des);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        desSellerAssets += result;

        query = "UPDATE assets SET " + des + " = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(desSellerAssets));
            pstmt.setString(2, seller);

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

        double adminPart = 0;
        query = "SELECT " + des + " FROM assets WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "admin");

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    adminPart = rs.getDouble(des);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        adminPart += exchangeTax;

        query = "UPDATE assets SET " + des + " = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, adminPart);
            pstmt.setString(2, "admin");

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

        // Add to history page then remove it.

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

        query = "INSERT INTO history VALUES(?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(offer.getId()));
            pstmt.setString(2, stringTime);
            pstmt.setString(3, LoginController.getUsername());
            pstmt.setString(4, seller);
            pstmt.setString(5, src);
            pstmt.setString(6, des);
            pstmt.setString(7, String.valueOf(price));

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

        query = "DELETE FROM offers WHERE id = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(offer.getId()));

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

        controller.removeOffer(offer);
    }
}

