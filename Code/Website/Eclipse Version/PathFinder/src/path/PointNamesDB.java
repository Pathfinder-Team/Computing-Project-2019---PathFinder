package path;
/* 
 Authors: Kevin Dunne, Jekaterina Pavlenko
 Date: 7/4/19
 Program: Website for enterprise application development
 */

import java.sql.*;
import java.util.ArrayList;

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
@WebServlet(name = "PointNamesDB", urlPatterns =
{
    "/PointNamesDB"
})
public class PointNamesDB extends HttpServlet
{
	
    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    ResultSet result;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    int maps_map_id = 0;
	int current_point_id = 0;
	String point_name = "";
	String org_building = "";
	int map_id = 0;
	String map_name = "";
	
	//new
	ArrayList<Integer> pointsFromOptions = new ArrayList<>();
	ArrayList<Integer> pointsToOptions = new ArrayList<>();
	ArrayList<Integer> pointsWeightOptions = new ArrayList<>();
	ArrayList<Node> special1 = new ArrayList<>();
	ArrayList<String> buildingName = new ArrayList<>();
	ArrayList<Node> buildingFloor = new ArrayList<>();
	//
	ArrayList<String> directionOptions = new ArrayList<>();
	getRankPower rp = new getRankPower();
	
    public void init() throws ServletException
    {
    	SQLConnection connect = new SQLConnection();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(connect.URL, connect.USERNAME, connect.PASSWORD);
            
            System.out.println("Connected AddPointsDB");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	
    	//System.out.println("rp.getUserNameRights() AddPointsDB: "+ rp.getUserNameRights());

    	String org_name = request.getParameter("org_name");
    	//System.out.println("org_name: "+org_name);
    	String organisation_building_name = request.getParameter("organisation_building_name");
    	//System.out.println("organisation_building_name: "+organisation_building_name);
		try {
			prepStat = conn.prepareStatement("select map_id,map_name from maps where org_building = ?");
			prepStat.setString(1, organisation_building_name);
			result = prepStat.executeQuery();
			buildingFloor.clear();
			while (result.next()) {
				
				map_id = result.getInt("map_id");
				map_name = result.getString("map_name");

				
				Node specialEdge = new Node(map_id,map_name);
				
				if(!buildingFloor.contains(map_id))
				{

					buildingFloor.add(specialEdge);
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error 10 ae: " + e);
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
				+ "<br>\r\n");
                out.println(""
                + "<br>\r\n"
                + "<h3>Add New Nodes</h3>\n"
                + "<form action=\"AddThings\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
                + "<fieldset>\n"
                + "<legend>Add New Points to Map</legend>\n"
                + "<br>\n"
                + "<p>Current Organisation: "+org_name
                + "<p>Current Building: "+organisation_building_name
                + "\n");
                
                out.println(""
                + "<p><label for=\"maps_map_id\" class=\"title\">Building Floor: <span>*</span></label>\n"
                + "<select name=\"maps_map_id\">"
                + "<option value=\"\">Select</option>");
                for (int i = 0; i < buildingFloor.size();i++)
				{
                    out.println("<option value="+buildingFloor.get(i).floorId+">"+buildingFloor.get(i).floorName+"</option>");
				}
                out.println("</select>");
                
                out.println(""
                + "<p><label for=\"point_name\" class=\"title\" >Name of Point: <span>*</span></label>\n"
                + "<input type=\"text\" name=\"point_name\" id=\"point_name\" /required></p>\n"
                + "\n"
             	+ "<input type=\"hidden\" name=\"insertPoints\" id=\"insertPoints\" value=\"insertNode\"></p>\n"
                + "\n"
                + "<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
                + "</p>\n"
                + "</fieldset>\n"
                + "</form>\n<br><br>");
                

        out.println("</section>\n"
        		+ "<br><br>"
                + "</main>\n"
                + "<footer>"
                + "<p>PathFinder project 2019</p>"
                + "<p>Authors: Kevin Dunne,Jekaterina Pavlenko & Christopher Costelloe</p>"
                + "<p><img src=\"images/maze_ic.png\" alt=\"\" ></p>"
                + "</footer>"
                + "\n"
                + "</div>\n"
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
