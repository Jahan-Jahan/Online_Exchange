package org.example.onlineexchange;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileController implements Initializable {

    private static final Logger logger = Logger.getLogger(ProfileController.class.getName());

    private SignUpController signUpObject = new SignUpController();
    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Your-Password";

    private Parent root;
    private Stage stage;
    private Scene scene;

    public double dollar, euro, toman, yen, pound;

    public String username, newUsername, email, newEmail, phone, newPhone;

    @FXML
    private Button backBtn, embezzleBtn;
    @FXML
    private Label usernameLabel, emailLabel, phoneLabel, editLabel1,
            editLabel2, editLabel3, assetsLabel, exchangeLabel, changeImageLabel;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Line line1, line2, line3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setProfile();

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        username = LoginController.getUsername();

        editLabel1.setOnMouseEntered(event -> {
            editLabel1.setStyle("-fx-text-fill: #0437F2;");
        });
        editLabel1.setOnMouseExited(event -> {
            editLabel1.setStyle("-fx-text-fill: white;");
        });

        editLabel2.setOnMouseEntered(event -> {
            editLabel2.setStyle("-fx-text-fill: #0437F2;");
        });
        editLabel2.setOnMouseExited(event -> {
            editLabel2.setStyle("-fx-text-fill: white;");
        });

        editLabel3.setOnMouseEntered(event -> {
            editLabel3.setStyle("-fx-text-fill: #0437F2;");
        });
        editLabel3.setOnMouseExited(event -> {
            editLabel3.setStyle("-fx-text-fill: white;");
        });

        exchangeLabel.setOnMouseEntered(event -> {
            exchangeLabel.setStyle("-fx-text-fill: #0437F2;");
        });
        exchangeLabel.setOnMouseExited(event -> {
            exchangeLabel.setStyle("-fx-text-fill: white;");
        });

        assetsLabel.setOnMouseEntered(event -> {
            assetsLabel.setStyle("-fx-text-fill: gold;");
        });
        assetsLabel.setOnMouseExited(event -> {
            assetsLabel.setStyle("-fx-text-fill: white;");
        });

        profileImageView.setOnMouseEntered(event -> {
            profileImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(80,200,120,0.75), 7, 0.5, 0, 0);");
            changeImageLabel.setVisible(true);
        });
        profileImageView.setOnMouseExited(event -> {
            profileImageView.setStyle("-fx-effect: none;");
            changeImageLabel.setVisible(false);
        });

        if (LoginController.ISADMIN) {

            editLabel1.setStyle("-fx-text-fill: #b9b9b9;");
            editLabel1.setOnMouseClicked(null);
            editLabel1.setOnMouseEntered(null);
            line1.setVisible(true);

            editLabel2.setStyle("-fx-text-fill: #b9b9b9;");
            editLabel2.setOnMouseClicked(null);
            editLabel2.setOnMouseEntered(null);
            line2.setVisible(true);

            editLabel3.setStyle("-fx-text-fill: #b9b9b9;");
            editLabel3.setOnMouseClicked(null);
            editLabel3.setOnMouseEntered(null);
            line3.setVisible(true);

            embezzleBtn.setVisible(true);

        }

        // Animation for username label
        TranslateTransition translateTransitionUsername = new TranslateTransition();

        translateTransitionUsername.setDuration(Duration.millis(1200));

        translateTransitionUsername.setNode(usernameLabel);

        translateTransitionUsername.setByX(0);

        translateTransitionUsername.setToX(100);

        translateTransitionUsername.setCycleCount(1);

        translateTransitionUsername.setAutoReverse(false);

        translateTransitionUsername.play();

        // Animation for email label
        TranslateTransition translateTransitionEmail = new TranslateTransition();

        translateTransitionEmail.setDuration(Duration.millis(1200));

        translateTransitionEmail.setNode(emailLabel);

        translateTransitionEmail.setByX(0);

        translateTransitionEmail.setToX(100);

        translateTransitionEmail.setCycleCount(1);

        translateTransitionEmail.setAutoReverse(false);

        translateTransitionEmail.play();

        // Animation for phone label
        TranslateTransition translateTransitionPhone = new TranslateTransition();

        translateTransitionPhone.setDuration(Duration.millis(1200));

        translateTransitionPhone.setNode(phoneLabel);

        translateTransitionPhone.setByX(0);

        translateTransitionPhone.setToX(100);

        translateTransitionPhone.setCycleCount(1);

        translateTransitionPhone.setAutoReverse(false);

        translateTransitionPhone.play();

        // Animation assets label
        TranslateTransition translateTransitionAssets = new TranslateTransition();

        translateTransitionAssets.setDuration(Duration.millis(1200));

        translateTransitionAssets.setNode(assetsLabel);

        translateTransitionAssets.setByX(0);

        translateTransitionAssets.setToX(100);

        translateTransitionAssets.setCycleCount(1);

        translateTransitionAssets.setAutoReverse(false);

        translateTransitionAssets.play();

        initializeLabels(username);

        updateAssets();

    }
    public void initializeLabels(String currentUsername) {

        String updateQuery = "SELECT email, phoneNumber FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, currentUsername);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                email = resultSet.getString("email");
                phone = resultSet.getString("phoneNumber");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from the table.");
        }

        usernameLabel.setText(currentUsername);

        emailLabel.setText(email);

        phoneLabel.setText(phone);

        LoginController.setUsername(currentUsername);

    }

    public void setProfile() {

        String query = "SELECT profileImage FROM users WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, LoginController.getUsername());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    // Get image data
                    InputStream is = rs.getBinaryStream("profileImage");
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte[] byteArray = new byte[1024];
                    int bytesRead = -1;

                    while ((bytesRead = is.read(byteArray)) != -1) {
                        buffer.write(byteArray, 0, bytesRead);
                    }

                    byte[] imageBytes = buffer.toByteArray();
                    profileImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occur in retrieve the image profile from table.");
        }

    }

    public void clickOnEdit1() throws SQLException, IOException {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Change username");
        dialog.setHeaderText("New Username");
        dialog.setContentText("New username:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            newUsername = result.get();

            if (signUpObject.usernameValidation(newUsername)) {

                changeUsername();

                initializeLabels(newUsername);

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Validation");
                alert.setHeaderText("About the new username...");
                alert.setContentText("Please Enter valid username!");

                alert.show();

            }
        }

    }
    public void clickOnEdit2() throws SQLException, IOException {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Change email");
        dialog.setHeaderText("New Email");
        dialog.setContentText("New email:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            newEmail = result.get();

            if (signUpObject.emailValidation(newEmail)) {

                changeEmail();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Validation");
                alert.setHeaderText("About the new email...");
                alert.setContentText("Please Enter valid email!");

                alert.show();
            }
        }

    }
    public void clickOnEdit3() throws SQLException, IOException {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Change phone");
        dialog.setHeaderText("New Phone");
        dialog.setContentText("New phone:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            newPhone = result.get();

            if (signUpObject.phoneValidation(newPhone)) {

                changePhone();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Validation");
                alert.setHeaderText("About the new phone number...");
                alert.setContentText("Please Enter valid phone number!");

                alert.show();
            }
        }

    }
    public void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage/mainPage.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Main");

        stage.setScene(scene);

        stage.show();

    }
    public void changeUsername() {

        if (signUpObject.usernameValidation(newUsername)) {

            String updateQuery = "UPDATE users SET username = ? WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, username);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    logger.log(Level.INFO, "Update query has done successfully.");
                } else {
                    logger.log(Level.SEVERE, "An error occur in execute the update query.");
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

            String updateQuery2 = "UPDATE assets SET username = ? WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery2)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, username);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    logger.log(Level.INFO, "Update query has done successfully.");
                } else {
                    logger.log(Level.SEVERE, "An error occur in execute the update query.");
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

            String updateQuery3 = "UPDATE comments SET username = ? WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery3)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, username);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    logger.log(Level.INFO, "Update query has done successfully.");
                } else {
                    logger.log(Level.SEVERE, "An error occur in execute the update query.");
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

            String updateQuery4 = "UPDATE offers SET username = ? WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery4)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, username);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    logger.log(Level.INFO, "Update query has done successfully.");
                } else {
                    logger.log(Level.SEVERE, "An error occur in execute the update query.");
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

            String updateQuery5 = "UPDATE history seller username = ? WHERE seller = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery5)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, username);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    logger.log(Level.INFO, "Update query has done successfully.");
                } else {
                    logger.log(Level.SEVERE, "An error occur in execute the update query.");
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

            String updateQuery6 = "UPDATE history buyer username = ? WHERE buyer = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery6)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, username);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    logger.log(Level.INFO, "Update query has done successfully.");
                } else {
                    logger.log(Level.SEVERE, "An error occur in execute the update query.");
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

            String updateQuery7 = "UPDATE blog SET username = ? WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery7)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, username);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    logger.log(Level.INFO, "Update query has done successfully.");
                } else {
                    logger.log(Level.SEVERE, "An error occur in execute the update query.");
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Changing username");

            alert.setHeaderText("Validation username...");

            alert.setContentText("Please enter a valid username!");

            alert.show();

        }

    }
    public void changeEmail() {

        if (signUpObject.emailValidation(newEmail)) {

            String updateQuery = "UPDATE users SET email = ? WHERE email = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                pstmt.setString(1, newEmail);
                pstmt.setString(2, email);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    logger.log(Level.INFO, "Update query has done successfully.");
                } else {
                    logger.log(Level.SEVERE, "An error occur in execute the update query.");
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Changing email");

            alert.setHeaderText("Validation email...");

            alert.setContentText("Please enter a valid email!");

            alert.show();

        }

    }
    public void changePhone() throws IOException, SQLException {

        if (signUpObject.phoneValidation(newPhone)) {

            String updateQuery = "UPDATE users SET phoneNumber = ? WHERE phoneNumber = ?";

            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

                pstmt.setString(1, newPhone);
                pstmt.setString(2, phone);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    logger.log(Level.INFO, "Update query has done successfully.");
                } else {
                    logger.log(Level.SEVERE, "An error occur in execute the update query.");
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Changing phone");

            alert.setHeaderText("Validation phone...");

            alert.setContentText("Please enter a valid phone!");

            alert.show();

        }

    }
    public void goToExchangePage() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("exchange/exchange.fxml")));

        stage = (Stage) backBtn.getScene().getWindow();

        scene = new Scene(root);

        stage.setTitle("Exchange");

        stage.setScene(scene);

        stage.show();

    }
    public void addMoney(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addMoney/addMoney.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Add-Money");

        stage.setScene(scene);

        stage.show();

    }

    public void clickOnAssets() throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("assets/assets.fxml")));

        scene = new Scene(root);

        stage = (Stage) backBtn.getScene().getWindow();

        stage.setTitle("Assets");

        stage.setScene(scene);

        stage.show();

    }

    public void updateAssets() {

        String query = "SELECT * FROM assets WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, usernameLabel.getText());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    dollar = rs.getDouble("dollar");
                    euro = rs.getDouble("euro");
                    toman = rs.getDouble("toman");
                    yen = rs.getDouble("yen");
                    pound = rs.getDouble("pound");
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        query = "UPDATE assets SET dollar = ?, euro = ?, toman = ?, yen = ?, pound = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(dollar));
            pstmt.setString(2, String.valueOf(euro));
            pstmt.setString(3, String.valueOf(toman));
            pstmt.setString(4, String.valueOf(yen));
            pstmt.setString(5, String.valueOf(pound));
            pstmt.setString(6, usernameLabel.getText());

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

    }

    public void clickOnProfile() throws FileNotFoundException {

        stage = (Stage) backBtn.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        FileInputStream fis = null;
        Image image;

        if (file != null) {
            fis = new FileInputStream(file);
            image = new Image(file.toURI().toString());
            profileImageView.setImage(image);
        }

        String query = "UPDATE users SET profileImage = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            assert file != null;
            pstmt.setBinaryStream(1, fis, (int) file.length());
            pstmt.setString(2, LoginController.getUsername());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

    }

    public void embezzleByAdmin(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirm-embezzle");

        alert.setHeaderText(null);

        alert.setContentText("Are you about it?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            return;
        }

        double total = 0;

        double euroPrice = MainPageController.euroPrice;
        double tomanPrice = MainPageController.tomanPrice;
        double yenPrice = MainPageController.yenPrice;
        double poundPrice = MainPageController.poundPrice;

        String query = "SELECT * FROM assets;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    total += rs.getDouble("dollar") +
                            (rs.getDouble("euro") * euroPrice) +
                            (rs.getDouble("toman") * tomanPrice) +
                            (rs.getDouble("yen") * yenPrice) +
                            (rs.getDouble("pound") * poundPrice);
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table");
        }

        query = "UPDATE assets SET dollar = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(total));
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

        query = "UPDATE assets SET dollar = ?, euro = ?, toman = ?, yen = ?, pound = ? WHERE username != ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "0");
            pstmt.setString(2, "0");
            pstmt.setString(3, "0");
            pstmt.setString(4, "0");
            pstmt.setString(5, "0");
            pstmt.setString(6, "admin");

            int res = pstmt.executeUpdate();

            if (res > 0) {
                logger.log(Level.INFO, "Update query has done successfully.");
            } else {
                logger.log(Level.SEVERE, "An error occur in execute the update query.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in execute the update query.");
        }

    }

}
