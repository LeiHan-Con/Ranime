/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ranime.player;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.io.File;

/**
 *
 * @author Rivaldo
 */
public class PlayPage extends javax.swing.JFrame {
    
    private JFXPanel jfxPanel;
    private MediaPlayer mediaPlayer;
    private String urlVideo;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PlayPage.class.getName());

    /**
     * Creates new form PlayPage
     * @param videoPath
     */
    public PlayPage(String videoPath) {
        initComponents(); 
        
        this.urlVideo = videoPath; 
        this.setSize(1350, 700); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        // --- TAMBAHKAN SENSOR JENDELA INI ---
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Jika mesin video sedang jalan, hentikan dan hancurkan!
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                }
            }
        });
        // ------------------------------------

        panelVideoLayar.setLayout(new java.awt.BorderLayout());
        panelVideoLayar.removeAll(); 
        
        // --- JURUS ANTI-TIDUR JAVAFX ---
        // Mencegah mesin utama JavaFX mati saat jendela ditutup
        Platform.setImplicitExit(false);
        
        jfxPanel = new JFXPanel();
        panelVideoLayar.add(jfxPanel, java.awt.BorderLayout.CENTER);
        
        panelVideoLayar.revalidate();
        panelVideoLayar.repaint();

        Platform.runLater(() -> {
            siapkanVideo();
        });
        
        pasangLogikaTombol();
    }

    private void siapkanVideo() {
        try {
            File fileVideo = new File(urlVideo);
            Media media = new Media(fileVideo.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            StackPane root = new StackPane();
            root.setStyle("-fx-background-color: black;"); // Background hitam sinematik
            root.getChildren().add(mediaView);
            
            // 1. Buat Scene tanpa ukuran kaku
            Scene scene = new Scene(root); 
            jfxPanel.setScene(scene);
            
            // 2. KEMBALIKAN SIHIR AUTO-RESIZE
            // Video akan mengikuti seberapa besar jfxPanel yang ditarik oleh JFrame
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitHeightProperty().bind(scene.heightProperty());
            mediaView.setPreserveRatio(true);
            
            // 3. Tetap gunakan setOnReady agar tidak layar hitam lagi
            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play(); 
                btnPlayPause.setText("Pause");
            });
            
        } catch (Exception e) {
            System.out.println("Gagal memutar video: " + e.getMessage());
        }
    }

    private void pasangLogikaTombol() {
        // 1. Play / Pause
        btnPlayPause.addActionListener(evt -> {
            Platform.runLater(() -> {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                    btnPlayPause.setText("Play");
                } else {
                    mediaPlayer.play();
                    btnPlayPause.setText("Pause");
                }
            });
        });

        // 2. Maju 5 Detik
        btnMaju.addActionListener(evt -> {
            Platform.runLater(() -> mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(5))));
        });

        // 3. Mundur 5 Detik
        btnMundur.addActionListener(evt -> {
            Platform.runLater(() -> mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.seconds(5))));
        });

        // 4. Volume dengan Slider
        sliderVolume.addChangeListener(evt -> {
            Platform.runLater(() -> mediaPlayer.setVolume(sliderVolume.getValue() / 100.0));
        });

        // 5. Fullscreen / Maximize
        btnFullscreen.addActionListener(evt -> {
            if (this.getExtendedState() == javax.swing.JFrame.MAXIMIZED_BOTH) {
                this.setExtendedState(javax.swing.JFrame.NORMAL); 
                btnFullscreen.setText("Fullscreen");
            } else {
                this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); 
                btnFullscreen.setText("Exit Fullscreen");
            }
        });
        
        // 6. Tombol Next dan Prev Eps (Hentikan video, lalu tutup window)
        btnNextEps.addActionListener(evt -> {
            if (mediaPlayer != null) mediaPlayer.stop();
            this.dispose(); 
            System.out.println("Tombol Next Eps ditekan!");
        });
        
        btnPrevEps.addActionListener(evt -> {
            if (mediaPlayer != null) mediaPlayer.stop();
            this.dispose(); 
            System.out.println("Tombol Prev Eps ditekan!");
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelKontrol = new javax.swing.JPanel();
        btnPrevEps = new javax.swing.JButton();
        btnMundur = new javax.swing.JButton();
        btnPlayPause = new javax.swing.JButton();
        btnMaju = new javax.swing.JButton();
        btnNextEps = new javax.swing.JButton();
        btnFullscreen = new javax.swing.JButton();
        sliderVolume = new javax.swing.JSlider();
        panelVideoLayar = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        panelKontrol.setPreferredSize(new java.awt.Dimension(1350, 32));

        btnPrevEps.setText("Prev Eps");
        panelKontrol.add(btnPrevEps);

        btnMundur.setText("<< 5s");
        panelKontrol.add(btnMundur);

        btnPlayPause.setText("Play");
        panelKontrol.add(btnPlayPause);

        btnMaju.setText("5s >>");
        panelKontrol.add(btnMaju);

        btnNextEps.setText("Next Eps");
        panelKontrol.add(btnNextEps);

        btnFullscreen.setText("Fullscreen");
        panelKontrol.add(btnFullscreen);

        sliderVolume.setValue(100);
        panelKontrol.add(sliderVolume);

        getContentPane().add(panelKontrol, java.awt.BorderLayout.SOUTH);

        panelVideoLayar.setPreferredSize(new java.awt.Dimension(1350, 700));

        javax.swing.GroupLayout panelVideoLayarLayout = new javax.swing.GroupLayout(panelVideoLayar);
        panelVideoLayar.setLayout(panelVideoLayarLayout);
        panelVideoLayarLayout.setHorizontalGroup(
            panelVideoLayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        panelVideoLayarLayout.setVerticalGroup(
            panelVideoLayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
        );

        getContentPane().add(panelVideoLayar, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PlayPage("").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFullscreen;
    private javax.swing.JButton btnMaju;
    private javax.swing.JButton btnMundur;
    private javax.swing.JButton btnNextEps;
    private javax.swing.JButton btnPlayPause;
    private javax.swing.JButton btnPrevEps;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelVideoLayar;
    private javax.swing.JSlider sliderVolume;
    // End of variables declaration//GEN-END:variables
}
