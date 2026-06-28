/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ranime.dao;

import com.ranime.model.Anime;
import com.ranime.database.Konek;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rivaldo
 */
public class AnimeDAO {
    public List<Anime> getAllAnime() {
        List<Anime> list = new ArrayList<>();
        try (Connection conn = Konek.connect()) {
            String sql = "SELECT * FROM anime";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            
            while(rs.next()) {
                Anime a = new Anime(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("genre"),
                    rs.getInt("episode"),
                    rs.getString("status"),
                    rs.getString("sinopsis"),
                    rs.getString("image_path"),
                    rs.getString("folder_path")
                );
                list.add(a);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
