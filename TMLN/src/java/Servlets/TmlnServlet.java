/************************************
 *  Document   : TmlnServlet
 *  Created on : 06.05.2019
 *  Author     : Michael Ulz
 *  Project    : TMLN
************************************/
package Servlets;

import Beans.TMLNEntry;
import de.umass.lastfm.Artist;
import de.umass.lastfm.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
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
    // set static variables and the List for the x-coordinates that will be the dates/months/years shown in the graph
    private static final String KEY = "4d2f280d1bdd14ca03f7383532c38d7f";
    private static final ZoneId TIMEZONE = ZoneId.systemDefault();
    private LinkedList<Object> xList;

    public TmlnServlet() {
        
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
        
        //set default values for the period input & set Attributes to avoid null-pointers
        LocalDate today = LocalDate.now();
        request.getSession().setAttribute("dateTo",today.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        request.getSession().setAttribute("dateFrom",today.minusWeeks(4).format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        request.setAttribute("data", "");
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
//        String user = request.getParameter("username").equals("") ? "iMichi8" : request.getParameter("username"); //Testuser - only for testing purposes
        xList = new LinkedList<>();
        
        // read parameters
        String user = request.getParameter("username"); 
//        int type = Integer.parseInt(request.getParameter("type")); // when track/album support is added
        int scale = Integer.parseInt(request.getParameter("scale"));

        // Sweet Alert if no username was put in
        PrintWriter out = response.getWriter();
        if (user.isEmpty()) {
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>");
            out.println(" <script src=\"https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js\"></script>");
            out.println("<script>");
            out.println("$(document).ready(function(){");
            out.println(" swal ('Oops' , 'Please enter a username!' , 'error')");
            out.println(" });");
            out.println("</script>");
        }
        
        // read period and convert to epoch-timestamp
        String fromDate = request.getParameter("from");
        String toDate = request.getParameter("to");
        LocalDateTime fromTime = LocalDateTime.parse(fromDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime toTime = LocalDateTime.parse(toDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long from = fromTime.atZone(TIMEZONE).toEpochSecond();
        long to = toTime.atZone(TIMEZONE).toEpochSecond();

        // break down the period into the read in scale and get the appropriate data for the graph
        LinkedList<TMLNEntry> dataList = getData(scale, user, fromTime, toTime);
        
        // fetching data from Last-FM API
        List<Artist> lastfmChart = ((List<Artist>)User.getWeeklyArtistChart(user, Long.toString(from), Long.toString(to), 0, KEY).getEntries());
        List<Artist> lastfmTop10 = lastfmChart.size()>=10?lastfmChart.subList(0, 10):lastfmChart.subList(0, lastfmChart.size());
        // calculation of the top 10 for the whole read in period
        TMLNEntry[] top10Entries = new TMLNEntry[10];
        int i = 0;
        for (Artist lastfmArtist : lastfmTop10) {
            top10Entries[i++] = new TMLNEntry(i, lastfmArtist.getName(), lastfmArtist.getPlaycount(), getColor(i));
            // set colors of the top 10 displayed in the chart
            for (TMLNEntry entry : dataList) {
                if(entry.getArtist().equals(lastfmArtist.getName())) {
                    entry.setColor(getColor(i));
                }
            }
        }
        
        // set attributes and forward to jsp
        request.getSession().setAttribute("dateTo",toTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        request.getSession().setAttribute("dateFrom",fromTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        request.setAttribute("xList", xList);
        request.setAttribute("data", dataList);
        request.getSession().setAttribute("top10", top10Entries);
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
    
    private LinkedList<TMLNEntry> getData(int scale, String user, LocalDateTime fromTime, LocalDateTime toTime) {
        int i;
        boolean exists;
        LinkedList<TMLNEntry> dataList = new LinkedList<>();
        
        long from;
        // set from-timestamp for the first timespan depending on the given scale
        switch(scale) {
            case 1: from = toTime.with(DayOfWeek.MONDAY).atZone(TIMEZONE).toEpochSecond(); break;
            case 2: from = toTime.withDayOfMonth(1).atZone(TIMEZONE).toEpochSecond(); break;
            case 3: from = toTime.withDayOfYear(1).atZone(TIMEZONE).toEpochSecond(); break;
            default: from=0;
        } 
        long to = toTime.atZone(TIMEZONE).toEpochSecond();
        
        // set a variable on the last week/month/year-start to check if it's full or opened later
        long start;
        switch(scale) {
            case 1: start = fromTime.with(DayOfWeek.MONDAY).atZone(TIMEZONE).toEpochSecond(); break;
            case 2: start = fromTime.withDayOfMonth(1).atZone(TIMEZONE).toEpochSecond(); break;
            case 3: start = fromTime.withDayOfYear(1).atZone(TIMEZONE).toEpochSecond(); break;
            default: start=0;
        } 

        // loop that checks if time is still in the given period 
        while (from >= fromTime.atZone(TIMEZONE).toEpochSecond()) {
            // fetching data from Last-FM API
            List<Artist> lastfmChart = ((List<Artist>)User.getWeeklyArtistChart(user, Long.toString(from), Long.toString(to), 0, KEY).getEntries());
            List<Artist> lastfmTop10 = lastfmChart.size()>=10?lastfmChart.subList(0, 10):lastfmChart.subList(0, lastfmChart.size());
            // reverse the top 10 because the chart displays data in reverse
            Collections.reverse(lastfmTop10);
            
            // set the String that'll be the x-coordinate for the datapoints
            String day = Instant.ofEpochSecond(from).atZone(TIMEZONE).toLocalDate().toString();
            String month = Instant.ofEpochSecond(from).atZone(TIMEZONE).toLocalDate().getMonth().toString();
            String year = Instant.ofEpochSecond(from).atZone(TIMEZONE).toLocalDate().getYear()+"";
            
            // loop through last-fm top 10 and add its artists to the dataList
            i = 10;
            for (Artist lastfmArtist : lastfmTop10) 
            {
                exists = false;
                for (TMLNEntry entry : dataList) 
                {
                    // ToDo: set every entries x-coords value per default to 11 or null?
                    // check if the entry is already in the list so it won't display twice and set it's coordinates
                    if (lastfmArtist.getName().equals(entry.getArtist())) 
                    {
                        Map<Object, Object> coords = new HashMap<>(); 
                        switch(scale) {
                            case 1: coords.put("x", day); break;
                            case 2: coords.put("x", month+" "+year); break;
                            case 3: coords.put("x", year); break;
                            default: coords.put("x", from);
                        } 
                        coords.put("y", i);
                        entry.getCoordsList().add(coords);
                        exists = true;
                        break;
                    } 
                }
                // check if last if handled the entry already and add new entry if not
                if (!exists) 
                {
                    dataList.add(new TMLNEntry(lastfmArtist.getName(), new ArrayList<>(),new int[] {211,211,211}));
                    Map<Object, Object> coords = new HashMap<>(); 
                    switch(scale) {
                        case 1: coords.put("x", day); break;
                        case 2: coords.put("x", month+" "+year); break;
                        case 3: coords.put("x", year); break;
                        default: coords.put("x", from);
                    } 
                    coords.put("y", i);
                    dataList.getLast().getCoordsList().add(coords);
                }
                i--;
            }
            
            // reset the timespan to one week/month/year before depending on the input
            to = from;
            switch(scale) {
                case 1:
                    xList.add(day);
                    from -= 604800;
                    break;
                case 2:
                    xList.add(month+" "+year);
                    from = LocalDateTime.ofInstant(Instant.ofEpochSecond(from - 86400), TIMEZONE).withDayOfMonth(1).atZone(TIMEZONE).toEpochSecond();
                    break;
                case 3:
                    xList.add(year); 
                    from = LocalDateTime.ofInstant(Instant.ofEpochSecond(from - 86400), TIMEZONE).withDayOfYear(1).atZone(TIMEZONE).toEpochSecond();
                    break;
                default:
                    from = 0;
            }
            // check if the loop would end
            if(from <= fromTime.atZone(TIMEZONE).toEpochSecond()){ 
                // check if it's an opened week/month/year, which need another run through the loop in order to include all data
                if(fromTime.atZone(TIMEZONE).toEpochSecond()==start) {
                    from -= 1;
                } else {
                    from = fromTime.atZone(TIMEZONE).toEpochSecond();
                    start = from;
                }
            }
        } 
        // reverse the x-Coordinates because the chart displays data in reverse
        Collections.reverse(xList); 
        //return full data
        return dataList;
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
