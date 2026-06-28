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
    
    //Variabel global
    private String judulAnime;
    private String genreAnime;
    private String posterPath;
    private String basePath;
    private String ekstensi;
    private int currentEps;
    
    private int idAnime;
    
    // Variabel untuk Slider Waktu
    private javax.swing.JSlider sliderWaktu;
    private javax.swing.JLabel lblWaktu;
    private javax.swing.JPanel panelWaktu;
    
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
        
        // ---> TAMBAHKAN BARIS INI DI SINI <---
        aturTombolNavigasi(this.urlVideo);
        // -------------------------------------
        
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
        
        // --- KODE BARU: Membuat Panel Waktu (Slider + Label) ---
        panelWaktu = new javax.swing.JPanel(new java.awt.BorderLayout());
        panelWaktu.setBackground(java.awt.Color.BLACK); // Agar menyatu dengan video
        
        // Setup Slider
        sliderWaktu = new javax.swing.JSlider(0, 100, 0);
        sliderWaktu.setBackground(java.awt.Color.BLACK);
        
        // Setup Label Teks
        lblWaktu = new javax.swing.JLabel("00:00 / 00:00");
        lblWaktu.setForeground(java.awt.Color.WHITE);
        lblWaktu.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 15)); // Beri jarak kanan
        
        // Susun ke dalam panel
        panelWaktu.add(sliderWaktu, java.awt.BorderLayout.CENTER);
        panelWaktu.add(lblWaktu, java.awt.BorderLayout.EAST);
        
        // Tempelkan di bagian BAWAH layar video (di atas panelKontrol)
        panelVideoLayar.add(panelWaktu, java.awt.BorderLayout.SOUTH);
        // --------------------------------------------------------
        
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
            
            // Cek di mana Java saat ini "berdiri" (Working Directory)
            System.out.println("Working Directory: " + System.getProperty("user.dir"));

            // Cek apakah file benar-benar ada di alamat tersebut
            java.io.File f = new java.io.File(urlVideo);
            System.out.println("Mencari file di: " + f.getAbsolutePath());
            System.out.println("Ketemu? " + f.exists());
            
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
                // Set batas maksimum slider sesuai durasi video
                sliderWaktu.setMaximum((int) mediaPlayer.getTotalDuration().toSeconds());
                
                mediaPlayer.play(); 
                btnPlayPause.setText("Pause");
            });
            
            // 4. KODE BARU: Mendengarkan pergerakan waktu video
            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                // Geser slider secara otomatis JIKA user tidak sedang menyeretnya
                if (!sliderWaktu.getValueIsAdjusting()) {
                    sliderWaktu.setValue((int) newValue.toSeconds());
                }
                
                // Ambil format teks
                String waktuSekarang = formatWaktu(newValue);
                String totalDurasi = formatWaktu(mediaPlayer.getTotalDuration());
                
                // Gunakan SwingUtilities agar UI Swing tidak bentrok dengan thread JavaFX
                javax.swing.SwingUtilities.invokeLater(() -> {
                    lblWaktu.setText(waktuSekarang + " / " + totalDurasi);
                });
            });
            
        } catch (Exception e) {
            System.out.println("Gagal memutar video: " + e.getMessage());
        }
    }
    
    public void updateInfoAnime(String judul, String genre, String episode, String imagePath) {
        // 1. Update Teks
        lblJudul.setText(judul);
        lblGenre.setText("Genre: " + genre);
        lblEpisode.setText("Episode: " + episode);
        
        this.judulAnime = judul;
        this.genreAnime = genre;
        this.posterPath = imagePath;

        // 2. Update Thumbnail
        // Menggunakan path dari database untuk menampilkan gambar
        try {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imagePath);
            // Resize gambar agar pas dengan lblThumbnail (misal 100x150)
            java.awt.Image img = icon.getImage().getScaledInstance(100, 150, java.awt.Image.SCALE_SMOOTH);
            lblThumbnail.setIcon(new javax.swing.ImageIcon(img));
            lblThumbnail.setText(""); // Hilangkan tulisan "Thumbnail" jika ada
        } catch (Exception e) {
            lblThumbnail.setText("No Image");
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
                // --- KELUAR DARI FULLSCREEN ---
                this.setExtendedState(javax.swing.JFrame.NORMAL); 
                btnFullscreen.setText("Fullscreen");

                // Munculkan kembali bagian atas (Header)
                panelHeader.setVisible(true);

            } else {
                // --- MASUK KE FULLSCREEN ---
                this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); 
                btnFullscreen.setText("Exit Fullscreen");

                // Sembunyikan bagian atas (Header) agar video penuh
                panelHeader.setVisible(false);
            }
        });
        
        // 6. Tombol Next dan Prev Eps (Hentikan video, buka episode baru, lalu tutup window)
        btnNextEps.addActionListener(evt -> {
            System.out.println("Tombol Next Eps ditekan!");
            
            // 1. Matikan video yang sedang berjalan
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            
            // 2. Rangkai path untuk episode selanjutnya
            int nextEps = currentEps + 1;
            String nextVideoPath = basePath + nextEps + ekstensi;
            
            // Panggil pencatatan history
            catatHistory(nextEps);
            
            // 3. Buka PlayPage baru dengan episode tujuan
            PlayPage pageBaru = new PlayPage(nextVideoPath);
            pageBaru.setIdAnime(idAnime);
            pageBaru.setJudulPage(judulAnime, nextEps);
            pageBaru.updateInfoAnime(judulAnime, genreAnime, String.valueOf(nextEps), posterPath);
            pageBaru.setVisible(true);
            
            // 4. Tutup halaman yang lama
            this.dispose(); 
        });
        
        btnPrevEps.addActionListener(evt -> {
            System.out.println("Tombol Prev Eps ditekan!");
            
            // 1. Matikan video yang sedang berjalan
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            
            // 2. Rangkai path untuk episode sebelumnya
            int prevEps = currentEps - 1;
            String prevVideoPath = basePath + prevEps + ekstensi;
            
            catatHistory(prevEps);
            
            // 3. Buka PlayPage baru dengan episode tujuan
            PlayPage pageBaru = new PlayPage(prevVideoPath);
            pageBaru.setIdAnime(idAnime);
            pageBaru.setJudulPage(judulAnime, prevEps);
            pageBaru.updateInfoAnime(judulAnime, genreAnime, String.valueOf(prevEps), posterPath);
            pageBaru.setVisible(true);
            
            // 4. Tutup halaman yang lama
            this.dispose(); 
        });
        
        // 7. Logika Slider Waktu (Maju/Mundur Video)
        sliderWaktu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                // Saat user melepas klik dari slider, suruh video loncat ke detik tersebut
                Platform.runLater(() -> {
                    mediaPlayer.seek(Duration.seconds(sliderWaktu.getValue()));
                });
            }
        });
    }
    
    private String formatWaktu(Duration durasi) {
        if (durasi == null || durasi.isUnknown()) {
            return "00:00";
        }
        int totalDetik = (int) Math.floor(durasi.toSeconds());
        int jam = totalDetik / 3600;
        int menit = (totalDetik % 3600) / 60;
        int detik = totalDetik % 60;
        
        if (jam > 0) {
            return String.format("%02d:%02d:%02d", jam, menit, detik);
        } else {
            return String.format("%02d:%02d", menit, detik);
        }
    }
    
    private void aturTombolNavigasi(String pathVideo) {
        try {
            // Mencari posisi teks "_eps" dan "."
            int indexEps = pathVideo.lastIndexOf("_eps");
            int indexTitik = pathVideo.lastIndexOf(".");
            
            if (indexEps != -1 && indexTitik != -1) {
                // Potong teks: "assets/anime/KaichouWaMaidSama_eps"
                basePath = pathVideo.substring(0, indexEps + 4); 
                // Potong teks: ".mp4"
                ekstensi = pathVideo.substring(indexTitik);      
                
                // Ambil angkanya dan ubah jadi Integer
                String epsString = pathVideo.substring(indexEps + 4, indexTitik);
                currentEps = Integer.parseInt(epsString);
                
                // 1. CEK EPS SEBELUMNYA (currentEps - 1)
                java.io.File filePrev = new java.io.File(basePath + (currentEps - 1) + ekstensi);
                // Jika file ada, tombol tampil. Jika tidak (misal ini eps 1), tombol hilang (false).
                btnPrevEps.setVisible(filePrev.exists()); 
                
                // 2. CEK EPS SELANJUTNYA (currentEps + 1)
                java.io.File fileNext = new java.io.File(basePath + (currentEps + 1) + ekstensi);
                // Jika file eps 2 ada, tombol tampil. Jika ini eps terakhir, tombol hilang.
                btnNextEps.setVisible(fileNext.exists()); 
                
                
            }
        } catch (Exception e) {
            System.out.println("Gagal mengatur navigasi: " + e.getMessage());
            btnPrevEps.setVisible(false);
            btnNextEps.setVisible(false);
        }
        
        this.setTitle(this.judulAnime + " - Episode " + currentEps);
    }
    
    public void setJudulPage(String judul, int episode) {
        this.judulAnime = judul;
        this.setTitle(judul + " - Episode " + episode);
    }
    
    // Ubah method ini agar menerima nomor episode
    private void catatHistory(int episode) {
        try {
            java.sql.Connection conn = com.ranime.database.Konek.connect();

            // Perintah sakti: Insert baru, kalau sudah ada (duplicate), update waktu tontonnya
            String sql = "INSERT INTO watched (user_id, anime_id, episode_tonton, watched_at) " +
                         "VALUES (?, ?, ?, NOW()) " +
                         "ON DUPLICATE KEY UPDATE watched_at = NOW()";

            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, com.ranime.auth.UserSession.getId());
            ps.setInt(2, this.idAnime);
            ps.setString(3, String.valueOf(episode));
            ps.executeUpdate();

            System.out.println("History berhasil diperbarui/disimpan!");
        } catch (Exception e) { 
            System.out.println("Gagal catat history: " + e.getMessage()); 
        }
    }
    
    public void setIdAnime(int id) { this.idAnime = id; }

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
        panelHeader = new javax.swing.JPanel();
        lblThumbnail = new javax.swing.JLabel();
        panelInfo = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        lblGenre = new javax.swing.JLabel();
        lblEpisode = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

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
            .addGap(0, 518, Short.MAX_VALUE)
        );

        getContentPane().add(panelVideoLayar, java.awt.BorderLayout.CENTER);

        panelHeader.setPreferredSize(new java.awt.Dimension(1350, 150));
        panelHeader.setLayout(new java.awt.BorderLayout());

        lblThumbnail.setText("Thumbnail");
        panelHeader.add(lblThumbnail, java.awt.BorderLayout.WEST);

        panelInfo.setPreferredSize(new java.awt.Dimension(738, 70));

        lblJudul.setText("Judul");

        lblGenre.setText("Genre");

        lblEpisode.setText("Episode");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("Kembali");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(lblJudul)
                        .addGap(0, 1225, Short.MAX_VALUE))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEpisode)
                            .addComponent(lblGenre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblJudul)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblGenre)
                        .addGap(18, 18, 18)
                        .addComponent(lblEpisode)
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );

        panelHeader.add(panelInfo, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelHeader, java.awt.BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        mediaPlayer.stop();
        mediaPlayer.dispose();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblEpisode;
    private javax.swing.JLabel lblGenre;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblThumbnail;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelKontrol;
    private javax.swing.JPanel panelVideoLayar;
    private javax.swing.JSlider sliderVolume;
    // End of variables declaration//GEN-END:variables
}
