/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ranime.model;

/**
 *
 * @author Rivaldo
 */
public class Anime {
    private int id;
    private String judul, genre, status, sinopsis, imagePath, folderPath;
    private int episode;

    public Anime(int id, String judul, String genre, int episode, String status, String sinopsis, String imagePath, String folderPath) {
        this.id = id;
        this.judul = judul;
        this.genre = genre;
        this.episode = episode;
        this.status = status;
        this.sinopsis = sinopsis;
        this.imagePath = imagePath;
        this.folderPath = folderPath;
    }

    // Getter untuk setiap atribut
    public int getId() { return id; }
    public String getJudul() { return judul; }
    public String getGenre() { return genre; }
    public int getEpisode() { return episode; }
    public String getStatus() { return status; }
    public String getSinopsis() { return sinopsis; }
    public String getImagePath() { return imagePath; }
    public String getFolderPath() { return folderPath; }
}
