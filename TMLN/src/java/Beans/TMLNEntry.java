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
public class TMLNEntry {
    int place;
    String title;
    String artist; 
    Color color;
    int playcount; 
    HashMap<Integer,Integer> coords;

    public TMLNEntry(int place, String title, String artist, Color color, int playcount) {
        this.place = place;
        this.title = title;
        this.artist = artist;
        this.color = color;
        this.playcount = playcount;
    }

    public TMLNEntry(int place, String artist, Color color, int playcount) {
        this.place = place;
        this.artist = artist;
        this.color = color;
        this.playcount = playcount;
    }

    public TMLNEntry(String title, String artist, HashMap<Integer, Integer> coords) {
        this.title = title;
        this.artist = artist;
        this.coords = coords;
    }

    public TMLNEntry(String artist, HashMap<Integer, Integer> coords) {
        this.artist = artist;
        this.coords = coords;
    }
}
