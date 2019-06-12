/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.TMLNArtist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Chart;
import de.umass.lastfm.Period;
import de.umass.lastfm.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author michi
 */
@WebServlet(name = "TmlnServlet", urlPatterns = {"/TmlnServlet"})
public class TmlnServlet extends HttpServlet {

    private static final String KEY = "4d2f280d1bdd14ca03f7383532c38d7f";
    private static final ZoneId TIMEZONE = ZoneId.systemDefault();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.getSession().setAttribute("top10", "");
        request.getRequestDispatcher("/tmlnjsp.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("username").equals("") ? "iMichi8" : request.getParameter("username"); //Testuser - nur zu Testzwecken
        Gson gson = new GsonBuilder().create();

        // Parameter einlesen
        // String user = request.getParameter("username"); 
        int type = Integer.parseInt(request.getParameter("type"));
        // ToDo: Range
        int scale = Integer.parseInt(request.getParameter("scale"));

        // Zeitraum einlesen und in Epoch-Format bringen
        // ToDo: Zeitraum einlesen
        LocalDateTime fromTime = LocalDateTime.parse("01.08.2018  00:00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss"));
        LocalDateTime toTime = LocalDateTime.parse("01.05.2019  00:00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss"));
        long from = fromTime.atZone(TIMEZONE).toEpochSecond();
        long to = toTime.atZone(TIMEZONE).toEpochSecond();

        // Berechnung der Top 10 Artists des Zeitraums
        Collection<Artist> top10LastfmArtists = User.getWeeklyArtistChart(user, Long.toString(from), Long.toString(to), 0, KEY).getEntries();
        TMLNArtist[] top10Artists = new TMLNArtist[10];
        int i = 0;
        for (Artist topArtist : top10LastfmArtists) {
            top10Artists[i++] = new TMLNArtist(i, topArtist.getName(), topArtist.getPlaycount());
            if (i == 10) {
                break;
            }
        }
        String data = gson.toJson(top10Artists);
        System.out.println(data);

        // ToDo: eingelesenen Zeitraum mit eingelesener Methode (Wöchentlich, Monatlich, Jährlich) runterbrechen
        switch (scale) {
            case 1: //ToDo: Weekly
                System.out.println(gson.toJson(getWeeklyData(user, fromTime, toTime)));
                break;
            case 2: //ToDo: Monthly

                break;
            case 3: //ToDo: Yearly

                break;
            default:
        }

        // ToDo: Erstellen der List für berechnete Zeiträume
        // Attribute setzen un an jsp weiterleiten
        request.getSession().setAttribute("top10", top10Artists);
        request.getRequestDispatcher("/tmlnjsp.jsp").forward(request, response);
    }

    private LinkedList<TMLNArtist> getWeeklyData(String user, LocalDateTime fromTime, LocalDateTime toTime) {
        long from = toTime.with(DayOfWeek.MONDAY).atZone(TIMEZONE).toEpochSecond();
        long to = toTime.atZone(TIMEZONE).toEpochSecond();
        int i;
        boolean exists;
        //TMLNArtist[] topArtists = new TMLNArtist[];
        LinkedList<TMLNArtist> topArtists = new LinkedList<>();
        HashMap<String, Integer> coords = new HashMap<>();

        while (from >= fromTime.atZone(TIMEZONE).toEpochSecond()) {
            Collection<Artist> lastfmCollection = User.getWeeklyArtistChart(user, Long.toString(from), Long.toString(to), 0, KEY).getEntries();
            i = 0;
            exists = false;
            for (Artist artist : lastfmCollection) 
            {
                for (TMLNArtist topArtist : topArtists) 
                {
                    if (artist.getName().equals(topArtist.getName())) 
                    {
                        topArtist.getCoords().put(Long.toString(from), i + 1);
                        exists = true;
                    } 
                    else 
                    {
                        topArtist.getCoords().put(Long.toString(from), 11);
                    }
                }

                if (!exists) 
                {
                    topArtists.add(new TMLNArtist(artist.getName(), coords));
                    topArtists.getLast().getCoords().put(Long.toString(from), i+1);
                    //topArtists[topArtists.length - 1].getCoords().put(Long.toString(from), i + 1);
                }
                if (i++ == 9) {
                    break;
                }
            }
            to = from;
            from -= 604800;
        }
        return topArtists;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
