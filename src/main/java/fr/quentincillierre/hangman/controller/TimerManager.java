package fr.quentincillierre.hangman.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TimerManager {

    private int timeRemaining;
    private Timeline timeline;

    public TimerManager(int seconds) {
        this.timeRemaining = seconds;
    }


    public void start(Runnable onTick, Runnable onFinish) {

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {

                timeRemaining--;

                onTick.run();

                if (timeRemaining <= 0) {
                    stop();
                    onFinish.run();
                }

            })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public void stop() {

        if (timeline != null) {
            timeline.stop();
        }

    }


    public void reset(int seconds) {

        stop();
        timeRemaining = seconds;

    }

    public void pause() {
    if (timeline != null) {
        timeline.pause();
    }
}

public void resume() {
    if (timeline != null) {
        timeline.play();
    }
}
    public int getTimeRemaining() {

        return timeRemaining;

    }
}