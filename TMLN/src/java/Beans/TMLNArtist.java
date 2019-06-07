/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Period;
import de.umass.lastfm.User;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author michi
 */
public class TMLNArtist {
    int place;
    String name, farbe; 
    int playcount, x, y; 
    
    public TMLNArtist(int place, String name, int playcount){
        this.place = place;
        this.name = name;
        this.playcount = playcount;
    }

    public TMLNArtist(String name, String farbe, int playcount, int x, int y) {
        this.name = name;
        this.farbe = farbe;
        this.playcount = playcount;
        this.x = x;
        this.y = y;
    }

    public TMLNArtist() {
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

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Artist{" + "name=" + name + ", farbe=" + farbe + ", playcount=" + playcount + ", x=" + x + ", y=" + y + '}';
    }
    
    
    
}
