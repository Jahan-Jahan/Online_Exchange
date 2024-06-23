module org.example.onlineexchange {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;
        requires java.mail;
        requires activation;
        requires javafx.media;

    opens org.example.onlineexchange to javafx.fxml;
        exports org.example.onlineexchange;
        }
