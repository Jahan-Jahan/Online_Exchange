module org.example.onlineexchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.onlineexchange to javafx.fxml;
    exports org.example.onlineexchange;
}