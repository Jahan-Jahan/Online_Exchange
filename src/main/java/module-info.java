module org.example.onlineexchange {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.onlineexchange to javafx.fxml;
    exports org.example.onlineexchange;
}