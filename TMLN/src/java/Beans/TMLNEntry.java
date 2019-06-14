/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.List;
import java.util.Map;

/**
 *
 * @author michi
 */
public class TMLNEntry {
    int place;
    String title;
    String artist;
    int playcount; 
    List<Map<Object,Object>> coordsList;
    int[] color;

    public TMLNEntry(int place, String title, String artist, int playcount, int[] color) {
        this.place = place;
        this.title = title;
        this.artist = artist;
        this.playcount = playcount;
        this.color = color;
    }

    public TMLNEntry(String title, String artist, List<Map<Object, Object>> coordsList, int[] color) {
        this.title = title;
        this.artist = artist;
        this.coordsList = coordsList;
        this.color = color;
    }

    public TMLNEntry(int place, String artist, int playcount, int[] color) {
        this.place = place;
        this.artist = artist;
        this.playcount = playcount;
        this.color = color;
    }

    public TMLNEntry(String artist, List<Map<Object, Object>> coordsList, int[] color) {
        this.artist = artist;
        this.coordsList = coordsList;
        this.color = color;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
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

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public List<Map<Object, Object>> getCoordsList() {
        return coordsList;
    }

    public void setCoordsList(List<Map<Object, Object>> coordsList) {
        this.coordsList = coordsList;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }
}
