/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.TMLNEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.umass.lastfm.Artist;
import de.umass.lastfm.User;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author michi
 */
public class GsonTest {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();
        HashMap<Integer,Integer> coords = new HashMap<>();
        coords.put(1, 2);
        coords.put(3, 4);
        
        
        
        String user = "iMichi8";
        String key = "4d2f280d1bdd14ca03f7383532c38d7f";
        ZoneId timezone = ZoneId.systemDefault();
        
        LocalDateTime fromTime = LocalDateTime.parse("01.01.2019  00:00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss"));
        LocalDateTime toTime = LocalDateTime.parse("01.05.2019  00:00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss"));
        String from = Long.toString(fromTime.atZone(timezone).toEpochSecond());
        String to = Long.toString(toTime.atZone(timezone).toEpochSecond());
        Collection<Artist> topLastfmArtists = User.getWeeklyArtistChart(user, from, to, 0, key).getEntries();
        TMLNEntry[] top10Artists = new TMLNEntry[10];
        int i = 0;
        for (Artist topArtist : topLastfmArtists) {
            top10Artists[i++] = new TMLNEntry(topArtist.getName(),coords);
            System.out.println(gson.toJson(top10Artists[i-1]));
            if(i==10) { break; }
        }
    }
}
