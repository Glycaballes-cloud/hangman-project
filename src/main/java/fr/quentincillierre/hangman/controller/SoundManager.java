package fr.quentincillierre.hangman.controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private MediaPlayer backgroundMusic;

    private AudioClip hover;
    private AudioClip click;
    private AudioClip correct;
    private AudioClip wrong;
    private AudioClip win;
    private AudioClip lose;
    private AudioClip timer;
    private AudioClip pause;

    public SoundManager() {

        hover = loadClip("/sounds/hover.wav");
        click = loadClip("/sounds/click.wav");
        correct = loadClip("/sounds/correct.wav");
        wrong = loadClip("/sounds/wrong.wav");
        win = loadClip("/sounds/win.wav");
        lose = loadClip("/sounds/lose.wav");
        timer = loadClip("/sounds/timer.wav");
        pause = loadClip("/sounds/pause.wav");
    }

    private AudioClip loadClip(String path) {

        try {
            return new AudioClip(
                    getClass()
                            .getResource(path)
                            .toExternalForm());

        } catch (Exception e) {

            System.out.println("Missing sound: " + path);
            return null;

        }

    }

    // -------------------------
    // Sound Effects
    // -------------------------

    public void playHover() {
        if (hover != null) hover.play();
    }

    public void playClick() {
        if (click != null) click.play();
    }

    public void playCorrect() {
        if (correct != null) correct.play();
    }

    public void playWrong() {
        if (wrong != null) wrong.play();
    }

    public void playWin() {
        if (win != null) win.play();
    }

    public void playLose() {
        if (lose != null) lose.play();
    }

    public void playTimer() {
        if (timer != null) timer.play();
    }

    public void playPause() {
        if (pause != null) pause.play();
    }

    // -------------------------
    // Background Music
    // -------------------------

    public void playBackground() {

        try {

            if (backgroundMusic == null) {

                Media media = new Media(
                        getClass()
                                .getResource("/sounds/background.mp3")
                                .toExternalForm());

                backgroundMusic = new MediaPlayer(media);
                backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
                backgroundMusic.setVolume(0.35);
            }

            backgroundMusic.play();

        } catch (Exception e) {

            System.out.println("Background music not found.");

        }
    }

    public void pauseBackground() {

        if (backgroundMusic != null) {
            backgroundMusic.pause();
        }

    }

    public void stopBackground() {

        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }

    }

    public boolean isBackgroundPlaying() {

        return backgroundMusic != null
                && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING;

    }

    public void toggleBackground() {

        if (backgroundMusic == null) {
            playBackground();
            return;
        }

        if (backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING) {
            backgroundMusic.pause();
        } else {
            backgroundMusic.play();
        }
    }

    public void setBackgroundVolume(double volume) {

        if (backgroundMusic != null) {
            backgroundMusic.setVolume(volume);
        }

    }

}