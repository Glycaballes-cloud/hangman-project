module fr.quentincillierre.hangman {

    // JavaFX Modules
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    // Open packages for FXML
    opens fr.quentincillierre.hangman.application to javafx.fxml;
    opens fr.quentincillierre.hangman.controller to javafx.fxml;

    // Export packages
    exports fr.quentincillierre.hangman.application;
    exports fr.quentincillierre.hangman.controller;
    exports fr.quentincillierre.hangman.model;

}