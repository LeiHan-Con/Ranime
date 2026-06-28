/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ranime.player;

import com.ranime.database.Konek;
import com.ranime.auth.LoginForm;
import com.ranime.auth.UserSession;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

/**
 *
 * @author Rivaldo
 */
public class Bookmarks extends BasePage {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Bookmarks.class.getName());

    /**
     * Creates new form HomePage
     */
    public Bookmarks() {
        initComponents();
        
        ImageIcon icon = new ImageIcon(
        getClass().getResource("/Images/Ranime_logo_50x50.png"));

            lblLogo.setIcon(icon);
            lblLogo.setText("");

        lblLogo.setIcon(icon);
        lblLogo.setText("");
        
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lblUsername.setText("Selamat Datang, " + UserSession.getUsername());
        
        setupNavigasi();
        loadData();
    }
    
    @Override
    public void setupNavigasi() {
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closePage();
                new HomePage().setVisible(true);
            }
        });

        btnWatched.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closePage();
                new Watched().setVisible(true);
            }
        });

        btnLogout.addActionListener(evt -> {
            int pilihan = JOptionPane.showConfirmDialog(null, "Yakin Logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if(pilihan == JOptionPane.YES_OPTION){
                UserSession.clear();
                closePage();
                new LoginForm().setVisible(true);
            }
        });
    }

    @Override
    public void loadData() {
        muatKatalogBookmarks();
    }

    private void muatKatalogBookmarks() {
        try {
            Connection conn = Konek.connect();
            // INNER JOIN hanya menampilkan anime yang ada di tabel bookmarks user ini
            String sql = "SELECT a.* FROM anime a " +
                         "INNER JOIN bookmarks b ON a.id = b.anime_id " +
                         "WHERE b.user_id = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, UserSession.getId());
            ResultSet rs = ps.executeQuery();

            panelKatalog.removeAll();

            while (rs.next()) {
                
                String genre = rs.getString("genre");
                String totalEpisode = String.valueOf(rs.getInt("episode"));
                String status = rs.getString("status");
                String sinopsis = rs.getString("sinopsis");
                String folderPath = rs.getString("folder_path");
                
                int idAnime = rs.getInt("id");
                String judul = rs.getString("judul");
                String imgPath = rs.getString("image_path");

                // Layout Item (Sama dengan HomePage)
                JPanel panelItem = new JPanel(new BorderLayout());
                panelItem.setPreferredSize(new Dimension(295, 240));
                panelItem.setBackground(new Color(45, 45, 45));

                JButton btnThumb = new JButton();
                btnThumb.setPreferredSize(new Dimension(295, 190));
                try {
                    ImageIcon icon = new ImageIcon(imgPath);
                    btnThumb.setIcon(new ImageIcon(icon.getImage().getScaledInstance(295, 190, java.awt.Image.SCALE_SMOOTH)));
                } catch (Exception ex) { btnThumb.setText("Poster"); }
                
                // --- TAMBAHKAN LOGIKA KLIK DI SINI ---
                btnThumb.addActionListener(evt -> {

                    // 1. Arahkan ke InfoPage
                    InfoPage info = new InfoPage();
                    info.muatDataInfo(idAnime, judul, genre, totalEpisode, status, sinopsis, imgPath, folderPath);
                    info.setVisible(true);
                });

                // Panel Judul & Bintang
                JPanel panelBawah = new JPanel(new BorderLayout(10, 0));
                panelBawah.setBackground(new Color(45, 45, 45));
                panelBawah.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                JLabel lblJudul = new JLabel(judul);
                lblJudul.setForeground(Color.WHITE);
                lblJudul.setFont(new java.awt.Font("Segoe UI", 1, 14));

                JLabel lblBintang = new JLabel("★", SwingConstants.CENTER);
                lblBintang.setFont(new java.awt.Font("Dialog", 1, 24));
                lblBintang.setForeground(Color.YELLOW); // Di halaman Bookmarks, bintang selalu kuning
                lblBintang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Logika Hapus Bookmark
                lblBintang.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        hapusBookmark(idAnime);
                        panelKatalog.remove(panelItem);
                        panelKatalog.revalidate();
                        panelKatalog.repaint();
                    }
                });

                panelBawah.add(lblJudul, BorderLayout.CENTER);
                panelBawah.add(lblBintang, BorderLayout.EAST);

                panelItem.add(btnThumb, BorderLayout.CENTER);
                panelItem.add(panelBawah, BorderLayout.SOUTH);

                panelKatalog.add(panelItem);
            }
            panelKatalog.revalidate();
            panelKatalog.repaint();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void hapusBookmark(int animeId) {
        try {
            Connection conn = Konek.connect();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM bookmarks WHERE user_id=? AND anime_id=?");
            ps.setInt(1, UserSession.getId());
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
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setText("Home");
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });

        btnBookmarks.setFont(new java.awt.Font("Segoe UI Historic", 1, 24)); // NOI18N
        btnBookmarks.setForeground(new java.awt.Color(51, 51, 255));
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
                .addGap(116, 116, 116)
                .addComponent(btnHome)
                .addGap(79, 79, 79)
                .addComponent(btnBookmarks)
                .addGap(45, 45, 45)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
    }//GEN-LAST:event_btnWatchedMouseClicked

    private void btnBookmarksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBookmarksMouseClicked
        // TODO add your handling code here:                         
    }//GEN-LAST:event_btnBookmarksMouseClicked

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeMouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new Bookmarks().setVisible(true));
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
