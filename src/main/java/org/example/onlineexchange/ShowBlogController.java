package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowBlogController implements Initializable {

    private static final Logger logger = Logger.getLogger(HistoryController.class.getName());

    private final String URL = "jdbc:mysql://localhost:3306/crypto";
    private final String USERNAME = "root";
    private final String PASSWORD = "Abolfazl_84";

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button backBtn;
    @FXML
    private TabPane tabPane;

    private List<BlogPost> blogPosts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String username;

        if (SearchCell.resultSearchUsername != null) {
            username = SearchCell.resultSearchUsername.split("-")[0];
        } else {
            username = LoginController.getUsername();
        }

        blogPosts = new ArrayList<>();

        String query = "SELECT * FROM blog WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    blogPosts.add(new BlogPost(rs.getString("title"),
                            rs.getString("username") + " said at " +
                                    rs.getString("time") + ":\n\n" + rs.getString("text")));
                }

            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occur in reading data from table.");
        }

        showBlog();
    }

    public void showBlog() {

        for (BlogPost blogPost : blogPosts) {
            Tab tab = new Tab();
            tab.setText(blogPost.getTitle());

            TextArea contentArea = new TextArea(blogPost.getContent());
            contentArea.setWrapText(true);
            contentArea.setEditable(false);

            tab.setContent(contentArea);
            tabPane.getTabs().add(tab);
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

    public static class BlogPost {
        private final String title;
        private final String content;

        public BlogPost(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }

}
