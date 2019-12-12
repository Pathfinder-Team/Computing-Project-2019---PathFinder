/* 
 Authors: Kevin Dunne, Jekaterina Pavlenko
 Date: 7/4/19
 Program: Website for enterprise application development
 */

import java.sql.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author: Kevin Dunne,Jekaterina Pavlenko
 */
@WebServlet(name = "AddPoints", urlPatterns =
{
    "/AddPoints"
})
public class AddPointsDB extends HttpServlet
{
	
    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public void init() throws ServletException
    {
        String URL = "jdbc:mysql://remotemysql.com:3306/4eyg55o51S?autoReconnect=true&useSSL=false";
        String USERNAME = "4eyg55o51S";
        String PASSWORD = "ADRFyeBfRn";

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected");
        }
        catch (Exception e)
        {
            System.err.println("Error 1" + e);
        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	String maps_map_id = request.getParameter("map_id");
    	String point_org = request.getParameter("organization_name");
    	String point_building_name = request.getParameter("organisation_building_name");
    	
    	System.out.println("special maps_map_id: "+maps_map_id);
    	
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		out.println("<!doctype html>\n"
				+ "<!-- Author: Jekaterina Pavlenko, Kevin Dunne, Christopher Costelloe Date: 09/03/2019-->"
				+ "<html lang=\"en\">" 
				+ "<head>" + "<meta charset=\"UTF-8\">" 
				+ "<title>Add Points Page</title>"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/mainstyle.css\" />"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/forum.css\" />" 
				+ "</head>"
				+ "<body>" 
				+ "<div id=\"container\">"
				+ "<header>"
				+ "<img src=\"images/bn_header.png\" alt=\"\" >" 
				+ "</header>"
				+ "<nav id=\"menu\">" 
				+ "<ul>"
				+ "<li><a href=\"index.html\" >ABOUT US</a></li>"
				+ "<li><a href=\"register.html\" >REGISTER</a></li>"
				+ "<li><a href=\"login.html\" >LOGIN</a></li>"
				+ "<li><a href=\"contact.html\" >CONTACT US</a></li>"
				+ "</ul>"
				+ "</nav>");
                out.println("<main>\n"
                + "                <section id=\"form\">\n"
                + "                    <ul class=\"sign_login\">\r\n"
                + "                        <li><a href=\"DetailsDB\">DETAILS</a></li>\r\n"
                + "                        <li><a href=\"Maps.jsp\">MAPS</a></li>\r\n"
                + "                        <li><a href=\"LogOutDB\" >LOG OUT</a></li>\r\n"
                + "						   <li><a href=\"ControlDB\">Control</a></li>\r\n"
                + "                    </ul>\r\n"
				+ "<br>\r\n" 
				+ "<br>\r\n"
                + "                    <h1>Edit</h1>\n"
                + "                    <form action=\"AddPointsAction\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
                + "                        <fieldset>\n"
                + "                            <legend>Add New Points to Map</legend>\n"
                + "                            <br>\n"
                + "\n"
                + "                            <p><label for=\"point_name\" class=\"title\" >point_name Name: <span>*</span></label>\n"
                + "                                <input type=\"text\" name=\"point_name\" id=\"point_name\" /required></p>\n"
                + "\n"
                + "                            <input type=\"hidden\" name=\"point_org\" id=\"point_org\" value="+point_org+"></p>\n"
                + "                            <input type=\"hidden\" name=\"maps_map_id\" id=\"maps_map_id\" value="+maps_map_id+"></p>\n"
                + "\n"
                + "                            <p><label for=\"point_weight\" class=\"title\">point_weight: <span>*</span></label>\n"
                + "                                <input type=\"number\" name=\"point_weight\" id=\"point_weight\" /required></p>\n"
                + "\n"
                + "                            <p><label for=\"organisation_building_name\" class=\"title\">organisation_building_name: <span>*</span></label>\n"
                + "                                <input type=\"text\" name=\"organisation_building_name\" id=\"organisation_building_name\" /required></p>\n"
                + "                            <p>\n"
        );
        
        out.println("<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
                + "                            </p>\n"
                + "                        </fieldset>\n"
                + "                    </form>\n");

        out.println("</section>\n"
                + "</main>\n"
                + "<footer>"
                + "<p>PathFinder project 2019</p>"
                + "<p>Authors: Kevin Dunne,Jekaterina Pavlenko & Christopher Costelloe</p>"
                + "<p><img src=\"images/maze_ic.png\" alt=\"\" ></p>"
                + "</footer>"
                + "\n"
                + "</div>\n"
                + "\n"
                + "</body>\n"
                + "</html>\n"
                + "");
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
