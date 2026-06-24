/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ranime.player;

/**
 *
 * @author Rivaldo
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;

public class VideoPlayer extends Application { // Pastikan nama class ini sama dengan nama file .java kamu

    @Override
    public void start(Stage primaryStage) {
        // 1. Tentukan lokasi file MP4 di laptopmu 
        // PENTING: Gunakan garis miring ke kanan (/) atau garis miring kiri ganda (\\)
        String videoPath = "C:\\Users\\Rivaldo\\OneDrive\\Videos\\Captures\\TestVideo.mp4"; 
        
        // 2. Buat object Media dan MediaPlayer
        File fileVideo = new File(videoPath);
        Media media = new Media(fileVideo.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        // 3. Masukkan ke dalam MediaView agar bisa ditampilkan
        MediaView mediaView = new MediaView(mediaPlayer);
        
        // Menyesuaikan ukuran video dengan layar (Opsional)
        mediaView.setFitWidth(800);
        mediaView.setFitHeight(600);
        mediaView.setPreserveRatio(true);

        // 4. Susun ke dalam layout UI
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);

        // 5. Tampilkan Window (Stage)
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Ranime - Video Player");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 6. Mainkan Video!
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        // Memulai aplikasi JavaFX
        launch(args);
    }
}
