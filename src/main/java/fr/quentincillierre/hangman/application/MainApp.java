package fr.quentincillierre.hangman.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        java.net.URL fxmlUrl = getClass().getResource("/fr/quentincillierre/hangman/application/menu-view.fxml");
        if (fxmlUrl == null) {
            throw new IllegalStateException("Cannot find FXML file: /fr/quentincillierre/hangman/application/menu-view.fxml");
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root, 1300, 800);

        java.net.URL styleUrl = getClass().getResource("/style.css");
        if (styleUrl != null) {
            scene.getStylesheets().add(styleUrl.toExternalForm());
        } else {
            System.out.println("Style CSS not found.");
        }

        primaryStage.setTitle("🤠 Wild West Hangman");

        java.io.InputStream iconStream = getClass().getResourceAsStream("/pictures/cowboy-icon.png");
        if (iconStream != null) {
            primaryStage.getIcons().add(new Image(iconStream));
        } else {
            System.out.println("Window icon not found.");
        }

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}