/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ranime.player;

/**
 *
 * @author Hype GLK
 */
import javax.swing.JOptionPane;
import com.ranime.auth.LoginForm;
import javax.swing.JOptionPane;
import com.ranime.auth.LoginForm;
import com.ranime.database.Konek;
import com.ranime.auth.UserSession;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class Watched extends BasePage {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Watched.class.getName());

    /**
     * Creates new form Watched
     */
    public Watched() {
        initComponents();
        
          ImageIcon icon = new ImageIcon(
        getClass().getResource("/Images/Ranime_logo_50x50.png"));

            lblLogo.setIcon(icon);
            lblLogo.setText("");
        
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lblUsername.setText("Selamat Datang, " + UserSession.getUsername());
        
        // 1. Mengikuti gaya layout HomePage (Jarak 28)
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 28, 28));
        
        // 2. Matikan scroll samping agar memaksa item turun ke bawah
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        // 3. Atur ukuran lebar mengikuti HomePage (1320), tingginya dilebihkan agar bisa di-scroll
        jPanel2.setPreferredSize(new java.awt.Dimension(1320, 2000)); 
        
        setupNavigasi();
        loadData();
    }
    
    @Override
    public void loadData(){
        loadWatched();
    }
    
    private void loadWatched() {
        try {
            Connection conn = Konek.connect();

            // Tambahkan w.episode_tonton di bagian SELECT
            // Query ini akan mengambil baris unik berdasarkan user, anime, dan episode
            String sql = "SELECT a.*, w.episode_tonton, MAX(w.watched_at) as last_watched " +
                        "FROM watched w " +
                        "JOIN anime a ON w.anime_id = a.id " +
                        "WHERE w.user_id = ? " +
                        "GROUP BY w.anime_id, w.episode_tonton " +
                        "ORDER BY last_watched DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, UserSession.getId());
            ResultSet rs = ps.executeQuery();

            jPanel2.removeAll();

            while(rs.next()){
                String judul = rs.getString("judul");
                String gambar = rs.getString("image_path");

                // Ambil data episode dari database
                String eps = rs.getString("episode_tonton");
                
                // Ambil waktu tonton
                String lastWatched = rs.getString("last_watched");
                
                if(lastWatched != null && lastWatched.length() > 16) {
                    lastWatched = lastWatched.substring(0, 16); // Ambil sampai menit saja
                }
                
                // Jika datanya kosong (misal riwayat lama sebelum fitur ini ada), jadikan strip "-"
                if(eps == null) eps = "-"; 

                JPanel card = new JPanel(new BorderLayout());
                card.setPreferredSize(new Dimension(220, 250));
                card.setBackground(Color.DARK_GRAY);

                JButton poster = new JButton();
                try {
                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(gambar);
                    java.awt.Image img = icon.getImage().getScaledInstance(220, 200, java.awt.Image.SCALE_SMOOTH);
                    poster.setIcon(new javax.swing.ImageIcon(img));
                } catch(Exception ex) {
                    poster.setText("No Image");
                }

                String teksLabel = "<html><div style='text-align: center;'>" + 
                                    judul + "<br>" +
                                    "<span style='color: yellow;'>Episode: " + eps + "</span><br>" +
                                    "<span style='color: lightgray; font-size: 10px;'>Terakhir: " + lastWatched + "</span>" +
                                    "</div></html>";
                JLabel lbl = new JLabel(teksLabel, SwingConstants.CENTER);
                lbl.setForeground(Color.WHITE);

                card.add(poster, BorderLayout.CENTER);
                card.add(lbl, BorderLayout.SOUTH);
                jPanel2.add(card);
            }

            jPanel2.revalidate();
            jPanel2.repaint();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void setupNavigasi() {
        // 1. Aksi Navigasi ke Home
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closePage();
                new HomePage().setVisible(true);
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHeader = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnHome = new javax.swing.JLabel();
        btnWatched = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        btnBookmarks = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelHeader.setBackground(new java.awt.Color(102, 102, 102));
        panelHeader.setPreferredSize(new java.awt.Dimension(708, 64));

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

        btnWatched.setFont(new java.awt.Font("Segoe UI Historic", 1, 24)); // NOI18N
        btnWatched.setForeground(new java.awt.Color(51, 51, 255));
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

        btnBookmarks.setForeground(new java.awt.Color(255, 255, 255));
        btnBookmarks.setText("Bookmarks");
        btnBookmarks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBookmarksMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblLogo)
                .addGap(116, 116, 116)
                .addComponent(btnHome)
                .addGap(107, 107, 107)
                .addComponent(btnBookmarks)
                .addGap(62, 62, 62)
                .addComponent(btnWatched)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout)
                .addGap(14, 14, 14))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogo)
                    .addComponent(btnHome)
                    .addComponent(btnLogout)
                    .addComponent(lblUsername)
                    .addComponent(btnBookmarks)
                    .addComponent(btnWatched))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 204));
        jScrollPane1.setHorizontalScrollBar(null);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1322, 702));

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setPreferredSize(new java.awt.Dimension(1320, 700));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1326, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 1350, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1338, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
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
        java.awt.EventQueue.invokeLater(() -> new Watched().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBookmarks;
    private javax.swing.JLabel btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel btnWatched;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel panelHeader;
    // End of variables declaration//GEN-END:variables
}
