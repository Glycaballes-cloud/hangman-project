package fr.quentincillierre.hangman.controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private MediaPlayer backgroundMusic;

    private final AudioClip hover;
    private final AudioClip click;
    private final AudioClip correct;
    private final AudioClip wrong;
    private final AudioClip win;
    private final AudioClip lose;
    private final AudioClip timer;
    private final AudioClip pause;

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

    // =====================================
    // SOUND EFFECTS
    // =====================================

    public void playHover() {
        playClip(hover);
    }

    public void playClick() {
        playClip(click);
    }

    public void playCorrect() {
        playClip(correct);
    }

    public void playWrong() {
        playClip(wrong);
    }

    public void playWin() {
        playClip(win);
    }

    public void playLose() {
        playClip(lose);
    }

    public void playTimer() {
        playClip(timer);
    }

    public void playPause() {
        playClip(pause);
    }

    private void playClip(AudioClip clip) {

        if (clip != null) {
            clip.play(1.0); // Full volume
        }
    }

    // =====================================
    // BACKGROUND MUSIC
    // =====================================

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

            if (backgroundMusic.getStatus() != MediaPlayer.Status.PLAYING) {
                backgroundMusic.play();
            }

        } catch (Exception e) {

            System.out.println("Background music not found.");
            e.printStackTrace();
        }
    }

    public void pauseBackground() {

        if (backgroundMusic != null
                && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING) {

            backgroundMusic.pause();
        }
    }

    public void stopBackground() {

        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
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

    public boolean isBackgroundPlaying() {

        return backgroundMusic != null
                && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public void setBackgroundVolume(double volume) {

        if (backgroundMusic != null) {
            backgroundMusic.setVolume(volume);
        }
    }
}