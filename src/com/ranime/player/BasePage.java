/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ranime.player;

import javax.swing.JFrame;

/**
 *
 * @author Rivaldo
 */
public abstract class BasePage extends JFrame{
    public abstract void setupNavigasi();
    public abstract void loadData();
    public void closePage() {
        this.dispose();
    }    
}
