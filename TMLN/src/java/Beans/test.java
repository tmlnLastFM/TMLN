package Beans;

import de.umass.lastfm.Period;
import de.umass.lastfm.User;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class test {

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
        System.out.println(playCounts + "\n");

        Artist[] artists = new Artist[10];
//        artists[0] = new Artist(artistNames.get(0), "Blau", playCounts.get(0), 5, 6);
//        System.out.println(artists[0].getName() + artists[0].getFarbe() + artists[0].getPlaycount());

        for (int i = 0; i < artists.length; i++) {
            artists[i] = new Artist(artistNames.get(i),playCounts.get(i));
        }
        System.out.println(artists[9].getName());
        
        Artist artist1 = new Artist(artists[0].getName(), "Blau", artists[0].getPlaycount(), 3, 4);
        System.out.println(artist1.toString());
        
    }
}
