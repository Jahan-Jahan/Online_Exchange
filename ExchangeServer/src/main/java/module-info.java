module org.example.exchangeserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens org.example.exchangeserver to javafx.fxml;
    exports org.example.exchangeserver;
}