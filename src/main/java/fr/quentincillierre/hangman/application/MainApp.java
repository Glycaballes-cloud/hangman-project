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

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("menu-view.fxml"));

        Parent root = loader.load();
            
        Scene scene = new Scene(root, 1300, 800);

        // Load CSS
        scene.getStylesheets().add(
                getClass()
                        .getResource("/style.css")
                        .toExternalForm());

        // Window Title
        primaryStage.setTitle("🤠 Wild West Hangman");

        // Window Icon
        try {
            primaryStage.getIcons().add(
                    new Image(getClass().getResourceAsStream("/pictures/cowboy-icon.png")));
        } catch (Exception e) {
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