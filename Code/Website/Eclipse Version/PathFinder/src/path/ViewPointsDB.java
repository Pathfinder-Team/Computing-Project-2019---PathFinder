package path;
/* 
 Authors: Kevin Dunne, Jekaterina Pavlenko
 Date: 7/4/19
 Program: Website for enterprise application development
 */

import java.sql.*;
import java.util.*;
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
    PreparedStatement prepStat1;
    ResultSet result;
    ResultSet result1;
    ArrayList<Node> specialArray = new ArrayList<>();
    int maps_map_id = 0;
	int current_point_id = 0;
	String point_name = "";
	LinkedList<Node> adj[];
	
	String org_name;
	String org_building;
	ArrayList<Node> map_points_array = new ArrayList();
	ArrayList<Node> points_array = new ArrayList();
    
    @Override
    public void init() throws ServletException
    {

    	SQLConnection connect = new SQLConnection();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(connect.URL, connect.USERNAME, connect.PASSWORD);
            
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }
    @SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
		org_name = request.getParameter("organisation_name");
		org_building = request.getParameter("organisation_building_name");
		
		org_building = "LIT Thurles";
		org_name = "Limerick Institute of Technology";
		
		System.out.println("org_name: "+org_name);
		System.out.println("org_building: "+org_building);
		
		try 
		{
			
		prepStat = conn.prepareStatement("select * from map_points join maps on maps.map_id=map_points.maps_map_id where org_name = ? and org_building = ?");
		prepStat.setString(1, org_name);
		prepStat.setString(2, org_building);
		result = prepStat.executeQuery();
		map_points_array.clear();
	    while(result.next())
	    {
	    	Node edge = new Node(result.getInt("current_point_id"),result.getString("point_name"),result.getInt("maps_map_id"));
	    	map_points_array.add(edge);
	    }
	    
		prepStat1 = conn.prepareStatement("select point_id,point_from_id,point_to_id,point_weight,point_direction from point_to join map_points on point_to.point_from_id = map_points.current_point_id join maps on maps.map_id = map_points.maps_map_id where org_name = ? and org_building =?");
		prepStat1.setString(1, org_name);
		prepStat1.setString(2, org_building);
		result1 = prepStat1.executeQuery();
		points_array.clear();
	    while(result1.next())
	    {
	    	Node edge = new Node(
	    			result1.getInt("point_id"),
	    			result1.getInt("point_from_id"),
	    			result1.getInt("point_to_id"),
	    			result1.getInt("point_weight"),
	    			result1.getString("point_direction"));
	    	points_array.add(edge);
	    }
	    
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		    System.out.println("Special Json error: "+e);
		}
    	//System.out.println("special "+maps_map_id);
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
                out.println(
						"<table class=\"comments\">"
				        + "<tr>");
                
		                for (int i = 0; i < map_points_array.size(); i++)
		                {		                    
			        		out.println("<th>current_point_id:" + map_points_array.get(i).current_point_id+"</th>"
					                + "<th>point_name:" + map_points_array.get(i).point_name+ "</th>"
					                + "<th>maps_map_id:" + map_points_array.get(i).maps_map_id+"</th>"
					                + "<tr>");
		                    for(int j = 0; j < points_array.size();j++)
		                    {
		                        if (map_points_array.get(i).current_point_id == points_array.get(j).point_from_id) {
				        		out.println("<th>point_from_id:" +points_array.get(j).point_from_id+"</th>"
						                + "<th>point_to_id:" + points_array.get(i).point_to_id+ "</th>"
						                + "<th>point_weight:" + points_array.get(i).point_weight+ "</th>"
						                + "<th>point_direction:" + points_array.get(i).point_direction+"</th>"
						                + "<tr>");
		                        }
		                    }
		                }
				        out.println("<form action=\"EditDB\" method=\"post\">"
				        + "<input type=\"hidden\" id=\"pageDirection\" name=\"pageDirection\" value=\"PostsDB\">"
				        + "<input type=\"submit\" value=\"Delete Post\">"
				        + "</form>"
				        + "</tr>"
				        + "<th>"
				        + "<form action=\"EditDB\" method=\"post\">"
				        + "<input type=\"hidden\" id=\"pageDirection\" name=\"pageDirection\" value=\"PostsDB\">"
				        + "<input type=\"submit\" value=\"Edit Post\">"
				        + "</form>"
				        + "</th>"
				        + "</tr>"
				        + "</table>"
				        + "<br>");
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
