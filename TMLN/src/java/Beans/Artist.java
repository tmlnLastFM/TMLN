/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import de.umass.lastfm.Period;
import de.umass.lastfm.User;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author michi
 */
public class Artist {
    
    public static void main(String[] args) {
        
        String key = "4d2f280d1bdd14ca03f7383532c38d7f";
        String user = "nici6120";
        
        Collection<de.umass.lastfm.Artist> topArtists = User.getTopArtists(user, Period.OVERALL, key);
        List<String> artistNames = new LinkedList<String>();
        List<Integer> playCounts = new LinkedList<Integer>();
        for (de.umass.lastfm.Artist topArtist : topArtists) {
            artistNames.add(topArtist.getName());
            playCounts.add(topArtist.getPlaycount());
        }
        
        
        System.out.println(artistNames);
        System.out.println(playCounts);
        
        
    }
    
    
    public Artist(String name, int playcount){
        this.name = name;
        this.playcount = playcount;
    }
    
    String name, farbe; 
    int playcount, x, y; 

    public Artist(String name, String farbe, int playcount, int x, int y) {
        this.name = name;
        this.farbe = farbe;
        this.playcount = playcount;
        this.x = x;
        this.y = y;
    }

    public Artist() {
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
