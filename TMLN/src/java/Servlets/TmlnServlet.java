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
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author michi
 */
@WebServlet(name = "TmlnServlet", urlPatterns = {"/TmlnServlet"})
public class TmlnServlet extends HttpServlet {

    private static final String KEY = "4d2f280d1bdd14ca03f7383532c38d7f";
    private static final ZoneId TIMEZONE = ZoneId.systemDefault();
    private LinkedList<Long> yList;

    public TmlnServlet() {
        this.yList = new LinkedList<>();
    }

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

        request.getSession().setAttribute("data", "");
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
        yList = new LinkedList<>();

        // Parameter einlesen
        // String user = request.getParameter("username"); 
        int type = Integer.parseInt(request.getParameter("type"));
        int scale = Integer.parseInt(request.getParameter("scale"));

        // Zeitraum einlesen und in Epoch-Format bringen
        String fromDate = request.getParameter("from");
        String toDate = request.getParameter("to");
        LocalDateTime fromTime = LocalDateTime.parse(fromDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime toTime = LocalDateTime.parse(toDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long from = fromTime.atZone(TIMEZONE).toEpochSecond();
        long to = toTime.atZone(TIMEZONE).toEpochSecond();

        // eingelesenen Zeitraum mit eingelesenem Scale (Wöchentlich, Monatlich, Jährlich) runterbrechen
        LinkedList<TMLNArtist> dataList = getData(scale, user, fromTime, toTime);
        
        // Berechnung der Top 10 Artists des Zeitraums
        List<Artist> lastfmChart = ((List<Artist>)User.getWeeklyArtistChart(user, Long.toString(from), Long.toString(to), 0, KEY).getEntries());
        List<Artist> lastfmTop10 = lastfmChart.size()>=10?lastfmChart.subList(0, 10):lastfmChart.subList(0, lastfmChart.size());  
        TMLNArtist[] top10Artists = new TMLNArtist[10];
        int i = 0;
        for (Artist lastfmArtist : lastfmTop10) {
            top10Artists[i++] = new TMLNArtist(i, lastfmArtist.getName(), lastfmArtist.getPlaycount(), getColor(i));
            // Farben der Top 10 setzen
            for (TMLNArtist artist : dataList) {
                if(artist.getName().equals(lastfmArtist.getName())) {
                    artist.setColor(getColor(i));
                }
            }
        }
        
        // Attribute setzen un an jsp weiterleiten
        Collections.reverse(yList);
        request.setAttribute("yList", yList);
        request.setAttribute("data", dataList);
        request.getSession().setAttribute("top10", top10Artists);
        request.getRequestDispatcher("/tmlnjsp.jsp").forward(request, response);
    }
    
    private int[] getColor(int i) {
        int[] rgb;
        switch (i) {
            case 1: rgb=new int[] {204,37,41}; break;
            case 2: rgb=new int[] {57,106,177}; break;
            case 3: rgb=new int[] {218,124,48}; break;
            case 4: rgb=new int[] {62,150,81}; break;
            case 5: rgb=new int[] {83,81,84}; break;
            case 6: rgb=new int[] {107,76,154}; break;
            case 7: rgb=new int[] {146,36,40}; break;
            case 8: rgb=new int[] {148,139,61}; break;
            case 9: rgb=new int[] {27,185,169}; break;
            case 10: rgb=new int[] {225,230,54}; break;
            default: rgb=new int[] {211,211,211};
        }
        return rgb;
    }
    
    private LinkedList<TMLNArtist> getData(int scale, String user, LocalDateTime fromTime, LocalDateTime toTime) {
        long from;
        switch(scale) {
            case 1: from = toTime.with(DayOfWeek.MONDAY).atZone(TIMEZONE).toEpochSecond(); break;
            case 2: from = toTime.withDayOfMonth(1).atZone(TIMEZONE).toEpochSecond(); break;
            case 3: from = toTime.withDayOfYear(1).atZone(TIMEZONE).toEpochSecond(); break;
            default: from=0;
        } 
        long to = toTime.atZone(TIMEZONE).toEpochSecond();
        int i;
        boolean exists, lastRun = false;
        LinkedList<TMLNArtist> allArtists = new LinkedList<>();

        while (from >= fromTime.atZone(TIMEZONE).toEpochSecond()) {
            List<Artist> lastfmChart = ((List<Artist>)User.getWeeklyArtistChart(user, Long.toString(from), Long.toString(to), 0, KEY).getEntries());
            List<Artist> lastfmTop10 = lastfmChart.size()>=10?lastfmChart.subList(0, 10):lastfmChart.subList(0, lastfmChart.size());
            Collections.reverse(lastfmTop10);
            i = 10;
            exists = false;
            for (Artist lastfmArtist : lastfmTop10) 
            {
                for (TMLNArtist artist : allArtists) 
                {
                    // ToDo: set every artists x-coords value per default to 11 
                    if (lastfmArtist.getName().equals(artist.getName())) 
                    {
                        Map<Object, Object> coords = new HashMap<Object,Object>(); coords.put("x", from); coords.put("y", i);
                        artist.getCoordsList().add(coords);
                        exists = true;
                        break;
                    } 
                }
                if (!exists) 
                {
                    allArtists.add(new TMLNArtist(lastfmArtist.getName(), new ArrayList<>(),new int[] {211,211,211}));
                    Map<Object, Object> coords = new HashMap<Object,Object>(); coords.put("x", from); coords.put("y", i);
                    allArtists.getLast().getCoordsList().add(coords);
                }
                i--;
            }
            to = from;
            yList.add(from); 
            switch(scale) {
                case 1: from -= 604800; break;
                case 2: from = LocalDateTime.ofInstant(Instant.ofEpochSecond(from-86400), TIMEZONE).withDayOfMonth(1).atZone(TIMEZONE).toEpochSecond(); break;
                case 3: from = LocalDateTime.ofInstant(Instant.ofEpochSecond(from-86400), TIMEZONE).withDayOfYear(1).atZone(TIMEZONE).toEpochSecond(); break;
                default: from=0;
            } 
            if(!lastRun && from < fromTime.atZone(TIMEZONE).toEpochSecond()) { 
                lastRun=true;
                from = fromTime.atZone(TIMEZONE).toEpochSecond();
            }
        } 
        return allArtists;
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
