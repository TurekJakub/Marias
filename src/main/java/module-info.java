module com.example.marias {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    exports com.example.marias.client;
    opens com.example.marias.client to javafx.fxml;
    exports com.example.marias.game;
    opens com.example.marias.game to javafx.fxml;
    exports com.example.marias.shared;
    opens com.example.marias.shared to javafx.fxml;
}