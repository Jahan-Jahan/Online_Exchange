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

public class OfferCell extends ListCell<OffersController.Offer> {

    private final OffersController controller;

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    @Override
    protected void updateItem(OffersController.Offer offer, boolean empty) {
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

    private void handlePurchaseOffer(OffersController.Offer offer) {

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
            e.printStackTrace();
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
                System.out.println("The purchase offer was successfully completed.");
            } else {
                System.out.println("There is a problem to buying.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        desSellerAssets += result;

        query = "UPDATE assets SET " + des + " = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(desSellerAssets));
            pstmt.setString(2, seller);

            int res = pstmt.executeUpdate();

            if (res > 0) {
                System.out.println("The purchase offer was successfully completed.");
            } else {
                System.out.println("There is a problem to buying.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
                System.out.println("This added to the history page.");
            } else {
                System.out.println("There is a problem to adding to the history.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "DELETE FROM offers WHERE id = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(offer.getId()));

            int res = pstmt.executeUpdate();

            if (res > 0) {
                System.out.println("The offer successfully deleted.");
            } else {
                System.out.println("There is a problem to deleting the offer.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        controller.removeOffer(offer);
    }
}

