/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ranime.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import LoginRegister.LoginForm;
import LoginRegister.UserSession;
import LoginRegister.Konek;

/**
 *
 * @author Rivaldo
 */
public class HomePage extends BasePage {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HomePage.class.getName());

    /**
     * Creates new form HomePage
     */
    public HomePage() {
        initComponents();
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lblUsername.setText("Selamat Datang, " + UserSession.getUsername());
        loadData();
    }
    
    @Override
    public void loadData() {
        muatKatalogDinamis();
    }
    
    private void muatKatalogDinamis() {
        try {
            // 1. Panggil koneksi database dari class Konek
            java.sql.Connection conn = Konek.connect();
            if (conn == null) {
                System.out.println("Gagal menyambung ke database saat memuat katalog.");
                return;
            }

            // 2. Tulis Query untuk mengambil seluruh data anime
            String sql = "SELECT * FROM anime";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();

            // Bersihkan panel utama dulu agar tidak ada data lama yang menumpuk
            panelKatalog.removeAll();

            // 3. Lakukan perulangan (looping) untuk membaca baris demi baris dari MySQL
            while (rs.next()) {
                // --- A. AMBIL DATA DARI DATABASE ---
                int idAnime = rs.getInt("id");
                String judul = rs.getString("judul");
                String genre = rs.getString("genre");
                String totalEpisode = String.valueOf(rs.getInt("episode")); 
                String status = rs.getString("status");     
                String sinopsis = rs.getString("sinopsis"); 
                String imgPath = rs.getString("image_path");
                String folderPath = rs.getString("folder_path"); 

                // --- B. BUAT KOTAK PEMBUNGKUS ITEM ---
                javax.swing.JPanel panelItem = new javax.swing.JPanel();
                panelItem.setLayout(new java.awt.BorderLayout()); 
                panelItem.setPreferredSize(new java.awt.Dimension(295, 215)); 
                panelItem.setBackground(new java.awt.Color(45, 45, 45)); // Warna background gelap agar elegan

                // --- C. BUAT TOMBOL THUMBNAIL ---
                javax.swing.JButton btnThumb = new javax.swing.JButton();
                btnThumb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // (Opsional) Langsung pasang gambar dari database ke tombol
                try {
                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imgPath);
                    java.awt.Image img = icon.getImage().getScaledInstance(295, 190, java.awt.Image.SCALE_SMOOTH);
                    btnThumb.setIcon(new javax.swing.ImageIcon(img));
                    btnThumb.setText(""); 
                } catch (Exception ex) {
                    btnThumb.setText("Poster"); // Jika gambar gagal dimuat
                }

                // --- D. EVENT KLIK TOMBOL (BUKA INFOPAGE) ---
                // Kita pakai lambda 'evt ->' agar variabel dari database bisa langsung dibaca
                btnThumb.addActionListener(evt -> {

                    // 1. KODE MENCATAT HISTORY KE DATABASE
                    try {
                        java.sql.Connection connHist = Konek.connect();
                        String sqlInsert = "INSERT INTO watched (user_id, anime_id) VALUES (?, ?)";
                        java.sql.PreparedStatement psInsert = connHist.prepareStatement(sqlInsert);

                        psInsert.setInt(1, UserSession.getId()); 
                        psInsert.setInt(2, idAnime); 

                        psInsert.executeUpdate();
                        System.out.println("Riwayat berhasil dicatat untuk anime ID: " + idAnime);
                    } catch (Exception e) {
                        System.out.println("Gagal mencatat riwayat: " + e.getMessage());
                    }

                    // 2. KODE MEMBUKA INFO PAGE (Bukan PlayPage!)
                    System.out.println("Membuka info untuk: " + judul);
                    InfoPage info = new InfoPage();
                    // Kirim semua data yang diambil tadi ke InfoPage
                    info.muatDataInfo(idAnime, judul, genre, totalEpisode, status, sinopsis, imgPath, folderPath);
                    info.setVisible(true); 

                });

                // --- E. BUAT TEKS JUDUL BAWAH ---
                javax.swing.JLabel lblJudul = new javax.swing.JLabel(judul, javax.swing.SwingConstants.CENTER);
                lblJudul.setForeground(java.awt.Color.WHITE);

                // --- F. SUSUN KE DALAM PANEL ---
                panelItem.add(btnThumb, java.awt.BorderLayout.CENTER);
                panelItem.add(lblJudul, java.awt.BorderLayout.SOUTH);

                // Masukkan kotak ini ke dalam wadah utama (panelKatalog)
                panelKatalog.add(panelItem);
            }

            // 4. Beritahu UI untuk memperbarui tampilan
            panelKatalog.revalidate();
            panelKatalog.repaint();

        } catch (Exception e) {
            System.out.println("Error saat memuat katalog dinamis: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnHome = new javax.swing.JLabel();
        btBbookmarks = new javax.swing.JLabel();
        btnWatched = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelKatalog = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home Page");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setText("Logo");

        btnHome.setBackground(new java.awt.Color(0, 0, 102));
        btnHome.setFont(new java.awt.Font("Segoe UI Historic", 1, 24)); // NOI18N
        btnHome.setForeground(new java.awt.Color(51, 51, 255));
        btnHome.setText("Home");

        btBbookmarks.setForeground(new java.awt.Color(255, 255, 255));
        btBbookmarks.setText("Bookmarks");
        btBbookmarks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btBbookmarksMouseClicked(evt);
            }
        });

        btnWatched.setForeground(new java.awt.Color(255, 255, 255));
        btnWatched.setText("Watched");
        btnWatched.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnWatchedMouseClicked(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        lblUsername.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("username");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblLogo)
                .addGap(103, 103, 103)
                .addComponent(btnHome)
                .addGap(85, 85, 85)
                .addComponent(btBbookmarks)
                .addGap(80, 80, 80)
                .addComponent(btnWatched)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogo)
                    .addComponent(btnHome)
                    .addComponent(btBbookmarks)
                    .addComponent(btnWatched)
                    .addComponent(btnLogout)
                    .addComponent(lblUsername))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jScrollPane1.setHorizontalScrollBar(null);

        panelKatalog.setBackground(new java.awt.Color(255, 255, 204));
        panelKatalog.setPreferredSize(new java.awt.Dimension(1320, 700));
        panelKatalog.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 28, 28));
        jScrollPane1.setViewportView(panelKatalog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1338, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setupNavigasi() {
        // 1. Aksi Navigasi ke Home
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Sudah di halaman Home, tidak perlu aksi (atau refresh)
            }
        });

        // 2. Aksi Navigasi ke Bookmarks
        btBbookmarks.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closePage();
                new Bookmarks().setVisible(true);
            }
        });

        // 3. Aksi Navigasi ke Watched
        btnWatched.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closePage();
                new Watched().setVisible(true);
            }
        });

        // 4. Aksi Logout
        btnLogout.addActionListener(evt -> {
            int pilihan = javax.swing.JOptionPane.showConfirmDialog(null, 
                    "Apakah Anda yakin ingin logout?", 
                    "Konfirmasi Logout", 
                    javax.swing.JOptionPane.YES_NO_OPTION);

            if(pilihan == javax.swing.JOptionPane.YES_OPTION){
                LoginRegister.UserSession.clear();
                closePage();
                new LoginRegister.LoginForm().setVisible(true);
            }
        });
    }
    
    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
         int pilihan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin logout?", 
                "Konfirmasi Logout", 
                JOptionPane.YES_NO_OPTION);

        if(pilihan == JOptionPane.YES_OPTION){
            UserSession.clear();
            this.dispose();
            new LoginForm().setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnWatchedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWatchedMouseClicked
        // TODO add your handling code here:
        this.dispose(); // tutup register                           
        new Watched().setVisible(true); // buka form watched
    }//GEN-LAST:event_btnWatchedMouseClicked

    private void btBbookmarksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btBbookmarksMouseClicked
        // TODO add your handling code here:
        this.dispose(); // tutup register                           
        new Bookmarks().setVisible(true); // buka form login
    }//GEN-LAST:event_btBbookmarksMouseClicked
    
    
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
        java.awt.EventQueue.invokeLater(() -> new HomePage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btBbookmarks;
    private javax.swing.JLabel btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel btnWatched;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel panelKatalog;
    // End of variables declaration//GEN-END:variables
}
