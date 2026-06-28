package com.ranime.database;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hype GLK
 */
    
import java.sql.Connection;
import java.sql.DriverManager;


public class Konek {
    public static Connection connect() {
        
        try {
            String url = "jdbc:mysql://localhost:3306/ranime_database";
            String user = "root";
            String pass = "";

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }

}
