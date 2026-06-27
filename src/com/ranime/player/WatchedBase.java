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

    public static void saveWatched(int userId, int animeId) {

        try {
            Connection conn = Konek.connect();

            String sql = "INSERT INTO watched(user_id, anime_id) VALUES(?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, animeId);

            ps.executeUpdate();

            ps.close();
            conn.close();

            System.out.println("Riwayat berhasil disimpan.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}