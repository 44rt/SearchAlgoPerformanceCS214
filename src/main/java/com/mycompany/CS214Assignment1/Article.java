/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CS214Assignment1;

/**
 *
 * @author S11199961
 */
public class Article {
    private int id;
    private String title;
    private String abstractText;
    private boolean[] subjects;

    public Article(int id, String title, String abstractText, boolean[] subjects) {
        this.id = id;
        this.title = title;
        this.abstractText = abstractText;
        this.subjects = subjects;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean[] getSubjects() {
        return subjects;
    }
}
