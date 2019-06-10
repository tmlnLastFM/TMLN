/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.awt.Color;
import java.util.HashMap;

/**
 *
 * @author michi
 */
public class TMLNTrack {
    
    String title, artist;
    Color color;
    int playcount;
    HashMap<Integer,Integer> coords;

    public TMLNTrack(String title, String artist, Color color, int playcount, HashMap<Integer, Integer> coords) {
        this.title = title;
        this.artist = artist;
        this.color = color;
        this.playcount = playcount;
        this.coords = coords;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public HashMap<Integer, Integer> getCoords() {
        return coords;
    }

    public void setCoords(HashMap<Integer, Integer> coords) {
        this.coords = coords;
    }
}
