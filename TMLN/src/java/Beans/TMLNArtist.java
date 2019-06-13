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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author michi
 */
public class TMLNArtist {
    int place;
    String name; 
    int playcount; 
    HashMap<String,Integer> coords;
    List<Map<Object,Object>> coordsList;
    Color color;
    
    public TMLNArtist(int place, String name, int playcount, Color color){
        this.place = place;
        this.name = name;
        this.playcount = playcount;
        this.color = color;
    }

    public TMLNArtist(String name, HashMap<String, Integer> coords, Color color) {
        this.name = name;
        this.coords = coords;
        this.color = color;
    }

    public TMLNArtist(String name, List<Map<Object, Object>> coordsList, Color color) {
        this.name = name;
        this.coordsList = coordsList;
        this.color = color;
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

    public HashMap<String, Integer> getCoords() {
        return coords;
    }

    public void setCoords(HashMap<String, Integer> coords) {
        this.coords = coords;
    }

    public List<Map<Object, Object>> getCoordsList() {
        return coordsList;
    }

    public void setCoordsList(List<Map<Object, Object>> coordsList) {
        this.coordsList = coordsList;
    }
}
