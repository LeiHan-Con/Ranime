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
import javax.swing.ImageIcon;
import com.ranime.auth.LoginForm;
import com.ranime.auth.UserSession;
import com.ranime.database.Konek;

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
       ImageIcon icon = new ImageIcon(
        getClass().getResource("/Images/Ranime_logo_50x50.png"));

            lblLogo.setIcon(icon);
            lblLogo.setText("");
        
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lblUsername.setText("Selamat Datang, " + UserSession.getUsername());
        
        loadData();
        setupNavigasi();
    }
    
    @Override
    public void loadData() {
        muatKatalogDinamis();
    }
    
    private void muatKatalogDinamis() {
        // Menggunakan DAO untuk enkapsulasi data
        com.ranime.dao.AnimeDAO dao = new com.ranime.dao.AnimeDAO();
        java.util.List<com.ranime.model.Anime> daftarAnime = dao.getAllAnime();

        panelKatalog.removeAll();

        for (com.ranime.model.Anime a : daftarAnime) {
            // --- B. BUAT KOTAK PEMBUNGKUS ITEM ---
            javax.swing.JPanel panelItem = new javax.swing.JPanel();
            panelItem.setLayout(new java.awt.BorderLayout());
            panelItem.setPreferredSize(new java.awt.Dimension(295, 240));
            panelItem.setBackground(new java.awt.Color(45, 45, 45));

            // --- C. BUAT TOMBOL THUMBNAIL ---
            javax.swing.JButton btnThumb = new javax.swing.JButton();
            btnThumb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            try {
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(a.getImagePath());
                java.awt.Image img = icon.getImage().getScaledInstance(295, 190, java.awt.Image.SCALE_SMOOTH);
                btnThumb.setIcon(new javax.swing.ImageIcon(img));
                btnThumb.setText("");
            } catch (Exception ex) {
                btnThumb.setText("Poster");
            }

            // Event Klik Poster
            btnThumb.addActionListener(evt -> {

                // Buka InfoPage
                InfoPage info = new InfoPage();
                info.muatDataInfo(a.getId(), a.getJudul(), a.getGenre(), 
                                  String.valueOf(a.getEpisode()), a.getStatus(), 
                                  a.getSinopsis(), a.getImagePath(), a.getFolderPath());
                info.setVisible(true);
            });

            // --- D. PANEL BAWAH (JUDUL & BINTANG) ---
            javax.swing.JPanel panelBawah = new javax.swing.JPanel(new java.awt.BorderLayout(10, 0));
            panelBawah.setBackground(new java.awt.Color(45, 45, 45));
            panelBawah.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

            javax.swing.JLabel lblJudul = new javax.swing.JLabel(a.getJudul(), javax.swing.SwingConstants.LEFT);
            lblJudul.setForeground(java.awt.Color.WHITE);
            lblJudul.setFont(new java.awt.Font("Segoe UI", 1, 14));

            javax.swing.JLabel lblBintang = new javax.swing.JLabel("★", javax.swing.SwingConstants.CENTER);
            lblBintang.setFont(new java.awt.Font("Dialog", 1, 22));
            lblBintang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            lblBintang.setPreferredSize(new java.awt.Dimension(30, 30));

            // Status awal
            final boolean[] isBookmarked = {isBookmarked(a.getId())};
            lblBintang.setForeground(isBookmarked[0] ? java.awt.Color.YELLOW : java.awt.Color.GRAY);

            lblBintang.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (isBookmarked[0]) {
                        hapusBookmark(a.getId());
                        lblBintang.setForeground(java.awt.Color.GRAY);
                        isBookmarked[0] = false;
                    } else {
                        simpanBookmark(a.getId());
                        lblBintang.setForeground(java.awt.Color.YELLOW);
                        isBookmarked[0] = true;
                    }
                }
            });

            panelBawah.add(lblJudul, java.awt.BorderLayout.CENTER);
            panelBawah.add(lblBintang, java.awt.BorderLayout.EAST);

            panelItem.add(btnThumb, java.awt.BorderLayout.CENTER);
            panelItem.add(panelBawah, java.awt.BorderLayout.SOUTH);
            panelKatalog.add(panelItem);
        }
        panelKatalog.revalidate();
        panelKatalog.repaint();
    }
    
    // 1. Cek apakah sudah di-bookmark
    private boolean isBookmarked(int animeId) {
        try {
            java.sql.Connection conn = com.ranime.database.Konek.connect();
            String sql = "SELECT * FROM bookmarks WHERE user_id=? AND anime_id=?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, com.ranime.auth.UserSession.getId());
            ps.setInt(2, animeId);
            return ps.executeQuery().next();
        } catch (Exception e) { return false; }
    }

    // 2. Simpan ke Database
    private void simpanBookmark(int animeId) {
        try {
            java.sql.Connection conn = com.ranime.database.Konek.connect();
            String sql = "INSERT INTO bookmarks (user_id, anime_id) VALUES (?, ?)";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, com.ranime.auth.UserSession.getId());
            ps.setInt(2, animeId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 3. Hapus dari Database
    private void hapusBookmark(int animeId) {
        try {
            java.sql.Connection conn = com.ranime.database.Konek.connect();
            String sql = "DELETE FROM bookmarks WHERE user_id=? AND anime_id=?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, com.ranime.auth.UserSession.getId());
            ps.setInt(2, animeId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
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
        btnBookmarks = new javax.swing.JLabel();
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
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Ranime_logo_50x50.png"))); // NOI18N

        btnHome.setBackground(new java.awt.Color(0, 0, 102));
        btnHome.setFont(new java.awt.Font("Segoe UI Historic", 1, 24)); // NOI18N
        btnHome.setForeground(new java.awt.Color(51, 51, 255));
        btnHome.setText("Home");

        btnBookmarks.setForeground(new java.awt.Color(255, 255, 255));
        btnBookmarks.setText("Bookmarks");
        btnBookmarks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBookmarksMouseClicked(evt);
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
                .addComponent(btnBookmarks)
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
                    .addComponent(btnBookmarks)
                    .addComponent(btnWatched)
                    .addComponent(btnLogout)
                    .addComponent(lblUsername))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
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
        btnBookmarks.addMouseListener(new java.awt.event.MouseAdapter() {
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
                com.ranime.auth.UserSession.clear();
                closePage();
                new com.ranime.auth.LoginForm().setVisible(true);
            }
        });
    }
    
    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnWatchedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWatchedMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnWatchedMouseClicked

    private void btnBookmarksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBookmarksMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBookmarksMouseClicked
    
    
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
    private javax.swing.JLabel btnBookmarks;
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
