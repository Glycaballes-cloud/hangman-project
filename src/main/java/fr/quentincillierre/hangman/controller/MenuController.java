package fr.quentincillierre.hangman.controller;

import java.net.URL;
import java.util.Optional;

import fr.quentincillierre.hangman.model.Category;
import fr.quentincillierre.hangman.model.Difficulty;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController {


    @FXML
    private ImageView backgroundImage;

    @FXML
    private ImageView titleImage;

    @FXML
    
    private TextField playerNameField;

    @FXML
private Button leaderboardButton;
    @FXML
private Button statisticsButton;

@FXML
private Button exitButton;


    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> difficultyComboBox;

    @FXML
    private Button startButton;

    @FXML
private void openLeaderboard() {
    soundManager.playClick();

    try {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/fr/quentincillierre/hangman/application/leaderboard-view.fxml"
                        )
                );

        Parent root = loader.load();

        changeScene(root);

    } catch (Exception e) {

        e.printStackTrace();

    }
}


    // Stores the current player
    private static String currentPlayerName = "Guest";
    private final SoundManager soundManager = new SoundManager();



    // ===========================================
    // Initialize
    // ===========================================

    @FXML
    public void initialize() {  

        addHoverEffect(startButton);
        addHoverEffect(leaderboardButton);
addHoverEffect(statisticsButton);
addHoverEffect(exitButton);

        loadImage(
                backgroundImage,
                "/pictures/background.png"
        );

        loadImage(
                titleImage,
                "/pictures/hangman-title.png"
        );


        categoryComboBox.setValue("Random");

        difficultyComboBox.setValue("Medium");
        soundManager.playBackground();

    }

    private void addHoverEffect(Button button) {

    button.setCursor(Cursor.HAND);

    button.setOnMouseEntered(e -> {

        soundManager.playHover();

        ScaleTransition st =
                new ScaleTransition(Duration.millis(150), button);

        st.setToX(1.08);
        st.setToY(1.08);

        st.play();

    });

    button.setOnMouseExited(e -> {

        ScaleTransition st =
                new ScaleTransition(Duration.millis(150), button);

        st.setToX(1);
        st.setToY(1);

        st.play();

    });

}


    // ===========================================
    // Exit Game
    // ===========================================

    @FXML
    private void exitGame() {
        soundManager.playClick();


        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION);


        alert.setTitle("Quit Game");

        alert.setHeaderText("Quit Current Game");

        alert.setContentText(
                "Are you sure you want to quit?"
        );


        DialogPane dialogPane =
                alert.getDialogPane();


        dialogPane.getStylesheets().add(
                getClass()
                .getResource("/style.css")
                .toExternalForm()
        );


        ButtonType yesButton =
                new ButtonType("YES");

        ButtonType noButton =
                new ButtonType("NO");


        alert.getButtonTypes()
                .setAll(
                        yesButton,
                        noButton
                );


        Optional<ButtonType> result =
                alert.showAndWait();


        if(result.isPresent()
                && result.get() == yesButton) {

            System.exit(0);

        }

    }



    // ===========================================
    // Start Game
    // ===========================================
 
    @FXML
    private void startGame() {
        soundManager.playClick();


        try {


            // Save player name
            currentPlayerName = getPlayerName();



            FXMLLoader loader =
                    new FXMLLoader(
                    getClass().getResource(
                    "/fr/quentincillierre/hangman/application/game-view.fxml")
                    );


            Parent root =
                    loader.load();



            GameController controller =
                    loader.getController();



            controller.setPlayerName(
                    currentPlayerName
            );


            controller.setCategory(
                    getSelectedCategory()
            );


            controller.setDifficulty(
                    getSelectedDifficulty()
            );


            controller.startGame();



            changeScene(root);



        } catch(Exception e) {

            e.printStackTrace();

        }

    }



    // ===========================================
    // Statistics
    // ===========================================

 @FXML
private void openStatistics() {
        soundManager.playClick();

    try {

        // Only update if user typed something
        String typedName = playerNameField.getText();

        if (typedName != null && !typedName.isBlank()) {
            currentPlayerName = typedName.trim();
        }

        System.out.println("Opening stats for: " + currentPlayerName);

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/fr/quentincillierre/hangman/application/statistics-view.fxml"
                        )
                );

        Parent root = loader.load();

        StatisticsController controller =
                loader.getController();

        controller.setPlayer(currentPlayerName);

        changeScene(root);

    } catch (Exception e) {

        e.printStackTrace();

    }

}

    // ===========================================
    // Change Scene
    // ===========================================

    private void changeScene(Parent root) {


        Stage stage =
                (Stage) startButton
                .getScene()
                .getWindow();


        stage.setScene(
                new Scene(root,1300,800)
        );

    }



    // ===========================================
    // Get Player Name
    // ===========================================

    private String getPlayerName() {


        String name =
                playerNameField.getText();



        if(name == null || name.isBlank()) {

            return "Guest";

        }



        return name.trim();

    }



    // ===========================================
    // Category
    // ===========================================

    private Category getSelectedCategory() {


        return Category.valueOf(
                categoryComboBox
                .getValue()
                .toUpperCase()
        );

    }



    // ===========================================
    // Difficulty
    // ===========================================

    private Difficulty getSelectedDifficulty() {


        return Difficulty.valueOf(
                difficultyComboBox
                .getValue()
                .toUpperCase()
        );

    }



    // ===========================================
    // Load Images
    // ===========================================

    private void loadImage(
            ImageView imageView,
            String path) {


        URL url =
                getClass()
                .getResource(path);



        if(url != null) {


            imageView.setImage(
                    new Image(
                    url.toExternalForm())
            );


        } else {


            System.out.println(
                    "Missing image : " + path
            );

        }

    }

}