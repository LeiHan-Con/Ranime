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
public class HomePage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HomePage.class.getName());

    /**
     * Creates new form HomePage
     */
    public HomePage() {
        initComponents();
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lblUsername.setText("Selamat Datang, " + UserSession.getUsername());
        muatKatalogDinamis(); // Tambahkan baris ini!
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
                    info.muatDataInfo(judul, genre, totalEpisode, status, sinopsis, imgPath, folderPath);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnbookmark = new javax.swing.JLabel();
        btnhistory = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelKatalog = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home Page");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Logo");

        jLabel2.setFont(new java.awt.Font("Segoe UI Historic", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Home");

        btnbookmark.setForeground(new java.awt.Color(255, 255, 255));
        btnbookmark.setText("Bookmarks");
        btnbookmark.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbookmarkMouseClicked(evt);
            }
        });

        btnhistory.setForeground(new java.awt.Color(255, 255, 255));
        btnhistory.setText("Watched");
        btnhistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhistoryMouseClicked(evt);
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
                .addComponent(jLabel1)
                .addGap(64, 64, 64)
                .addComponent(btnbookmark)
                .addGap(64, 64, 64)
                .addComponent(btnhistory)
                .addGap(441, 441, 441)
                .addComponent(jLabel2)
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
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(btnbookmark)
                    .addComponent(btnhistory)
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

    private void btnhistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhistoryMouseClicked
        // TODO add your handling code here:
        this.dispose(); // tutup register                           
        new Watched().setVisible(true); // buka form watched
    }//GEN-LAST:event_btnhistoryMouseClicked

    private void btnbookmarkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbookmarkMouseClicked
        // TODO add your handling code here:
        this.dispose(); // tutup register                           
        new Bookmarks().setVisible(true); // buka form login
    }//GEN-LAST:event_btnbookmarkMouseClicked

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
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel btnbookmark;
    private javax.swing.JLabel btnhistory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel panelKatalog;
    // End of variables declaration//GEN-END:variables
}
