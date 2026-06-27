/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ranime.player;

/**
 *
 * @author Hype GLK
 */
import LoginRegister.Konek;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class WatchedBase {

    public static void saveWatched(int userId, int animeId, String episode) {
        Connection conn = null;
        try {
            conn = Konek.connect();

            // 1. Hapus riwayat lama
            String sqlDelete = "DELETE FROM watched WHERE user_id=? AND anime_id=? AND episode_tonton=?";
            PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
            psDelete.setInt(1, userId);
            psDelete.setInt(2, animeId);
            psDelete.setString(3, episode);
            psDelete.executeUpdate();

            // 2. Insert data baru
            String sqlInsert = "INSERT INTO watched(user_id, anime_id, episode_tonton) VALUES(?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, userId);
            psInsert.setInt(2, animeId);
            psInsert.setString(3, episode);

            int rowsAffected = psInsert.executeUpdate(); // Cek apakah baris berhasil ditambah
            System.out.println("Data berhasil disimpan: " + (rowsAffected > 0));

        } catch (Exception e) {
            e.printStackTrace(); // Jika ada error, ini akan muncul di Output NetBeans
        } finally {
            // Pastikan koneksi ditutup
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}