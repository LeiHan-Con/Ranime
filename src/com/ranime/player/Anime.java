/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ranime.player;

/**
 *
 * @author Rivaldo
 */
public class Anime {
    private String judul;
    private String imagePath;
    private String videoPath;

    public Anime(String judul, String imagePath, String videoPath) {
        this.judul = judul;
        this.imagePath = imagePath;
        this.videoPath = videoPath;
    }

    public String getJudul() { return judul; }
    public String getImagePath() { return imagePath; }
    public String getVideoPath() { return videoPath; }
}
