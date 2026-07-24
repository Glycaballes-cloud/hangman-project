package fr.quentincillierre.hangman.controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AnimationManager {

    // 1. Shake animation (wrong guess)
    public void shake(Node node) {

        TranslateTransition shake = new TranslateTransition(Duration.millis(60), node);
        shake.setByX(8);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }

    // 2. Pop animation (correct guess)
    public void pop(Node node) {

        ScaleTransition pop = new ScaleTransition(Duration.millis(150), node);
        pop.setFromX(1);
        pop.setFromY(1);
        pop.setToX(1.2);
        pop.setToY(1.2);
        pop.setCycleCount(2);
        pop.setAutoReverse(true);
        pop.play();
    }

    // 3. Flash red (wrong guess)
    public void flash(Label label) {

        FadeTransition out = new FadeTransition(Duration.millis(120), label);
        out.setFromValue(1);
        out.setToValue(0.4);

        label.setTextFill(Color.RED);

        FadeTransition in = new FadeTransition(Duration.millis(120), label);
        in.setFromValue(0.4);
        in.setToValue(1);

        SequentialTransition sequence = new SequentialTransition(out, in);

        sequence.setOnFinished(e -> label.setTextFill(Color.BLACK));

        sequence.play();
    }

    // 4. Keyboard press animation
    public void press(Node node) {

        ScaleTransition press = new ScaleTransition(Duration.millis(100), node);
        press.setToX(0.85);
        press.setToY(0.85);
        press.setCycleCount(2);
        press.setAutoReverse(true);
        press.play();
    }

    // 5. Win jump
    public void jump(Node node) {

        TranslateTransition jump = new TranslateTransition(Duration.millis(250), node);
        jump.setByY(-35);
        jump.setCycleCount(2);
        jump.setAutoReverse(true);
        jump.play();
    }

    // 6. Lose swing
    public void swing(Node node) {

        javafx.animation.RotateTransition rotate =
                new javafx.animation.RotateTransition(Duration.millis(250), node);

        rotate.setByAngle(15);
        rotate.setCycleCount(8);
        rotate.setAutoReverse(true);
        rotate.play();
    }

    // 7. Fade in
    public void fadeIn(Node node) {

        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    // 8. Fade out
    public void fadeOut(Node node) {

        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), node);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
    }

    // 9. Timer pulse
    public Animation pulse(Node node) {

        ScaleTransition pulse = new ScaleTransition(Duration.millis(350), node);

        pulse.setToX(1.15);
        pulse.setToY(1.15);

        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);

        return pulse;
    }

    // 10. Hover animation
    public void installHover(Node node) {

        node.setOnMouseEntered(e -> {

            ScaleTransition st = new ScaleTransition(Duration.millis(120), node);
            st.setToX(1.08);
            st.setToY(1.08);
            st.play();

        });

        node.setOnMouseExited(e -> {

            ScaleTransition st = new ScaleTransition(Duration.millis(120), node);
            st.setToX(1);
            st.setToY(1);
            st.play();

        });
    }
}