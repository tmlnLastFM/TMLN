/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Period;
import de.umass.lastfm.User;
import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author michi
 */
public class TMLNArtist {
    int place;
    String name; 
    Color color;
    int playcount; 
    HashMap<Integer,Integer> coords;
    
    public TMLNArtist(int place, String name, int playcount){
        this.place = place;
        this.name = name;
        this.playcount = playcount;
    }

    public TMLNArtist(String name, HashMap<Integer, Integer> coords) {
        this.name = name;
        this.coords = coords;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
