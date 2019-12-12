package path;
/* 
 Authors: Kevin Dunne, Jekaterina Pavlenko
 Date: 7/4/19
 Program: Website for enterprise application development
 */

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
@WebServlet(name = "ViewPointsDB", urlPatterns =
{
    "/ViewPointsDB"
})
public class ViewPointsDB extends HttpServlet
{
	

    
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Connection conn;
    Statement stmt;
    PreparedStatement prepStat;
    ResultSet result;
    
    int maps_map_id = 0;
	int current_point_id = 0;
	String point_name = "";
    
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
    	//String maps_map_id = request.getParameter("imageId");
    	String point_org = request.getParameter("organisation_name");
    	String point_building_name = request.getParameter("organisation_building_name");
		String map_image_name = request.getParameter("maps_map_id");
    	
    	System.out.println("special map_image_name: "+map_image_name);
    	System.out.println("special point_org: "+point_org);
    	System.out.println("special point_building_name: "+point_building_name);
    	
    	
		try {
			prepStat = conn.prepareStatement("select map_id from maps where map_name = ?");
			prepStat.setString(1, map_image_name);
			result = prepStat.executeQuery();
		
			while (result.next()) {
				maps_map_id = result.getInt("map_id");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error 10 ae: " + e);
		}
		/*
		try {
			prepStat = conn.prepareStatement("select * from map points where maps_map_id = ?");
			prepStat.setString(1, map_image_name);
			result = prepStat.executeQuery();
		
			while (result.next()) {
				current_point_id = result.getInt("current_point_id");
				point_name = result.getString("point_name");
				maps_map_id = result.getInt("maps_map_id");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error 10 ae: " + e);
		}
		*/
		
    	System.out.println("special "+maps_map_id);
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
				+ "<br>"
				+ "<br>"
				+ "<br>");
                try
                {
                        stmt = conn.createStatement();
                        String sql = "select * from map_points";

                        result = stmt.executeQuery(sql);
                        while (result.next())
                        {
                            out.println("<table class=\"comments\">"
                                    + "<tr>"
                                    + "<th>current_point_id:" + result.getString("current_point_id") + "</th>"
                                    + "<th>point_name:" + result.getString("point_name") + "</th>"
                                    + "<th>maps_map_id: " + result.getString("maps_map_id") + "</th>"
                                    + "<th>"
                                    + "<form action=\"EditDB\" method=\"post\">"
                                    + "<input type=\"hidden\" id=\"pageDirection\" name=\"pageDirection\" value=\"PostsDB\">"
                                    + "<input type=\"submit\" value=\"Delete Post\">"
                                    + "</form>"
                                    + "</th>"
                                    + "<th>"
                                    + "<form action=\"EditDB\" method=\"post\">"
                                    + "<input type=\"hidden\" id=\"pageDirection\" name=\"pageDirection\" value=\"PostsDB\">"
                                    + "<input type=\"submit\" value=\"Edit Post\">"
                                    + "</form>"
                                    + "</th>"
                                    + "</tr>"
                                    + "</table>"
                                    + "<br>");

                        }

                    }catch (SQLException ex) {
        				System.err.println("Error 2: " + ex);
        			}
        out.println("</section>\n"
        		+ "<br>"
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
