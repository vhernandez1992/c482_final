module hernandez.c482_final {
    requires javafx.controls;
    requires javafx.fxml;


    opens hernandez.Main to javafx.fxml;
    exports hernandez.Main;
    exports controller;
    exports model;
    opens controller to javafx.fxml;
}